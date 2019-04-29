package baseball;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BaseballElimination {

  private final int[] win;
  private final int[] lost;
  private final int[] remaining;
  private final int[][] games;
  private final int teams;
  private final Map<String, Integer> teamToIndex;
  private final Map<Integer, String> indexToTeam;
  private final int bestTeam;
  private FordFulkerson minCut;

  public BaseballElimination(String filename) {
    In in = new In(filename);
    teams = in.readInt();
    win = new int[teams];
    lost = new int[teams];
    remaining = new int[teams];
    games = new int[teams][teams];
    teamToIndex = new HashMap<>();
    indexToTeam = new HashMap<>();
    in.readLine();
    int max = 0;
    for (int i = 0; i < teams; i++) {
      final String team = in.readString();
      teamToIndex.put(team, i);
      indexToTeam.put(i, team);
      win[i] = in.readInt();
      if (win[i] > win[max]) {
        max = i;
      }
      lost[i] = in.readInt();
      remaining[i] = in.readInt();
      for (int j = 0; j < teams; j++) {
        games[i][j] = in.readInt();
      }
      in.readLine();
    }
    bestTeam = max;
  }

  public int numberOfTeams() {
    return teams;
  }

  public Iterable<String> teams() {
    return teamToIndex.keySet();
  }

  public int wins(String team) {
    return win[index(team)];
  }

  public int losses(String team) {
    return lost[index(team)];
  }

  public int remaining(String team) {
    return remaining[index(team)];
  }

  public int against(String team1, String team2) {
    return games[index(team1)][index(team2)];
  }

  public boolean isEliminated(String team) {
    if (teams == 1) {
      return false;
    }
    int x = index(team);
    if (win[x] + remaining[x] < win[bestTeam]) {
      return true;
    }
    int noTeams = teams - 1;
    int noGames = noTeams * (noTeams - 1) / 2;
    int v = noGames + noTeams + 2;
    FlowNetwork flowNetwork = new FlowNetwork(v);
    int game = 1;
    for (int i = 0; i < noTeams - 1; i++) {
      int skipI = i >= x ? 1 : 0;
      for (int j = i + 1; j < noTeams; j++) {
        int skipJ = j >= x ? 1 : 0;
        flowNetwork.addEdge(new FlowEdge(0, game, games[i + skipI][j + skipJ]));
        flowNetwork.addEdge(new FlowEdge(game, noGames + 1 + i, Double.POSITIVE_INFINITY));
        flowNetwork.addEdge(new FlowEdge(game, noGames + 1 + j, Double.POSITIVE_INFINITY));
        game++;
      }
      flowNetwork.addEdge(new FlowEdge(noGames + 1 + i, v - 1, win[x] + remaining[x] - win[i + skipI]));
    }
    flowNetwork.addEdge(new FlowEdge(noGames + noTeams,
                                     v - 1,
                                     win[x] + remaining[x] - win[noTeams - 1 + (noTeams - 1 >= x ? 1 : 0)]));
    minCut = new FordFulkerson(flowNetwork, 0, v - 1);
    for (FlowEdge edge : flowNetwork.adj(0)) {
      if (Double.compare(edge.flow(), edge.capacity()) != 0) {
        return true;
      }
    }
    return false;
  }

  public Iterable<String> certificateOfElimination(String team) {
    if (teams == 1) {
      return null;
    }
    int x = index(team);
    Set<String> teamSet = new HashSet<>();
    if (win[x] + remaining[x] < win[bestTeam]) {
      teamSet.add(indexToTeam.get(bestTeam));
      return teamSet;
    } else if (isEliminated(team)) {
      int noTeams = teams - 1;
      int noGames = noTeams * (noTeams - 1) / 2;
      int v = noGames + noTeams + 2;
      int index = 0;
      for (int i = noGames + 1; i < v - 1; i++) {
        if (index == x) {
          index++;
        }
        if (minCut.inCut(i)) {
          teamSet.add(indexToTeam.get(index));
        }
        index++;
      }
      return teamSet;
    } else {
      return null;
    }
  }

  private int index(String team) {
    Integer i = teamToIndex.get(team);
    if (i == null) {
      throw new IllegalArgumentException();
    }
    return i;
  }

  public static void main(String[] args) {
    BaseballElimination division = new BaseballElimination(args[0]);
    for (String team : division.teams()) {
      if (division.isEliminated(team)) {
        StdOut.print(team + " is eliminated by the subset R = { ");
        for (String t : division.certificateOfElimination(team)) {
          StdOut.print(t + " ");
        }
        StdOut.println("}");
      } else {
        StdOut.println(team + " is not eliminated");
      }
    }
  }

}
