import java.util.*;

public class RoutePlanner {
    private Map<String, List<Route>> graph;

    static class Route {
        String destination;
        int distance;

        public Route(String destination, int distance) {
            this.destination = destination;
            this.distance = distance;
        }

        @Override
        public String toString() {
            return destination + " (" + distance + " km)";
        }
    }

    public RoutePlanner() {
        graph = new HashMap<>();
    }

    public void addLocation(String location) {
        graph.putIfAbsent(location, new ArrayList<>());
    }

    public void addRoad(String location1, String location2, int distance) {
        graph.putIfAbsent(location1, new ArrayList<>());
        graph.putIfAbsent(location2, new ArrayList<>());
        graph.get(location1).add(new Route(location2, distance));
        graph.get(location2).add(new Route(location1, distance));
    }

    public void displayGraph() {
        for (String location : graph.keySet()) {
            System.out.println(location + " -> " + graph.get(location));
        }
    }

    public void findShortestPath(String start, String end) {
        Queue<String> queue = new LinkedList<>();
        Map<String, String> previous = new HashMap<>();
        Set<String> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();

            if (current.equals(end)) {
                printPath(previous, start, end);
                return;
            }

            for (Route route : graph.get(current)) {
                if (!visited.contains(route.destination)) {
                    queue.add(route.destination);
                    visited.add(route.destination);
                    previous.put(route.destination, current);
                }
            }
        }

        System.out.println("No path found from " + start + " to " + end);
    }

    private void printPath(Map<String, String> previous, String start, String end) {
        List<String> path = new ArrayList<>();
        String current = end;

        while (current != null) {
            path.add(current);
            current = previous.get(current);
        }

        Collections.reverse(path);
        System.out.println("Shortest path: " + String.join(" -> ", path));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RoutePlanner planner = new RoutePlanner();

        System.out.print("Enter the number of locations: ");
        int numLocations = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < numLocations; i++) {
            System.out.print("Enter location name: ");
            String location = scanner.nextLine();
            planner.addLocation(location);
        }

        System.out.print("Enter the number of roads: ");
        int numRoads = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < numRoads; i++) {
            System.out.print("Enter road (format: Location1 Location2 Distance): ");
            String location1 = scanner.next();
            String location2 = scanner.next();
            int distance = scanner.nextInt();
            scanner.nextLine();
            planner.addRoad(location1, location2, distance);
        }

        System.out.println("Route Map:");
        planner.displayGraph();

        System.out.print("Enter start location: ");
        String start = scanner.nextLine();

        System.out.print("Enter destination: ");
        String end = scanner.nextLine();

        System.out.println("Finding the shortest path:");
        planner.findShortestPath(start, end);
    }
}
