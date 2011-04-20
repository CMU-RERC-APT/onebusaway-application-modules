package org.onebusaway.transit_data_federation.bundle.tasks.transfer_pattern;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.onebusaway.collections.tuple.Pair;
import org.onebusaway.collections.tuple.Tuples;
import org.onebusaway.gtfs.model.AgencyAndId;

public class TransferPatternData implements Serializable {

  private static final long serialVersionUID = 1L;

  private final Map<AgencyAndId, Set<Entry>> _stops = new HashMap<AgencyAndId, Set<Entry>>();

  private final Entry _root;

  public TransferPatternData(AgencyAndId origin) {
    _root = new Entry(origin, true, null, 0);
  }

  public void addPath(List<Pair<AgencyAndId>> path) {

    if (path.isEmpty())
      return;

    Entry node = _root;

    for (Pair<AgencyAndId> segment : path) {
      AgencyAndId from = segment.getFirst();
      AgencyAndId to = segment.getSecond();

      if (node == _root) {
        if (from != _root.stop)
          throw new IllegalStateException();
      } else {
        node = node.extend(from);
      }

      node = node.extend(to);
    }

    AgencyAndId stop = node.stop;
    Set<Entry> nodes = _stops.get(stop);
    if (nodes == null) {
      nodes = new HashSet<Entry>();
      _stops.put(stop, nodes);
    }
    nodes.add(node);
  }

  public Set<AgencyAndId> getStops() {
    return _stops.keySet();
  }

  public List<List<Pair<AgencyAndId>>> getPathsForStop(AgencyAndId stop) {

    List<List<Pair<AgencyAndId>>> paths = new ArrayList<List<Pair<AgencyAndId>>>();
    Set<Entry> nodes = _stops.get(stop);

    for (Entry node : nodes) {
      List<Pair<AgencyAndId>> path = new ArrayList<Pair<AgencyAndId>>();
      paths.add(path);
      while (node != null) {
        Entry b = node;
        Entry a = node.parent;
        if (a == null)
          throw new IllegalStateException();
        node = a.parent;
        path.add(0, Tuples.pair(a.stop, b.stop));
      }
    }

    return paths;
  }

  private static class Entry implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private final AgencyAndId stop;
    private final boolean transfer;
    private final Map<AgencyAndId, Entry> _children = new HashMap<AgencyAndId, TransferPatternData.Entry>();
    private final Entry parent;
    private final int depth;

    public Entry(AgencyAndId stop, boolean transfer, Entry parent, int depth) {
      if (stop == null)
        throw new IllegalArgumentException();
      this.stop = stop;
      this.transfer = transfer;
      this.parent = parent;
      this.depth = depth;
    }

    public Entry extend(AgencyAndId from) {
      Entry node = _children.get(from);
      if (node == null) {
        node = new Entry(from, !this.transfer, this, depth + 1);
        _children.put(from, node);
      }
      return node;
    }

    @Override
    public String toString() {
      return stop.getId() + " " + transfer;
    }
  }
}