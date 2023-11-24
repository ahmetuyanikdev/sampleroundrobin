
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SampleRoundRobin {

    public static void main(String args[]) {
        System.out.println("Test");

        List<Integer> request = Arrays.asList(3, 2, 4, 1, 5);
        List<Integer> requestBursTime = Arrays.asList(2, 4, 2, 3, 2);

        List<Integer> assignedServers = getAssignedServers(3, request, requestBursTime);
        System.out.println("---> assigned servers " + assignedServers);
    }

    public static List<Integer> getAssignedServers(int numOfServers, List<Integer> request, List<Integer> burstTimes) {

        int[] servers = IntStream.rangeClosed(1, numOfServers).toArray();
        int[] serverOccupations = new int[servers.length];
        Arrays.fill(serverOccupations, 0);
        List<Integer> assignedServers = new LinkedList<>();
        System.out.println(" servers : " + Arrays.toString(servers));
        System.out.println(" initial servers occupations : " + Arrays.toString(serverOccupations));

        List<Integer> sortedRequest = request.stream().sorted().collect(Collectors.toList());

        sortedRequest.forEach(req -> {
            int requestIndex = request.indexOf(req);
            int burstTime = burstTimes.get(requestIndex);
            int requestTotalTime = req + burstTime;
            OptionalInt optionalInteger = Arrays.stream(serverOccupations).filter(o -> o <= req).findFirst();
            int availableServerOccupation = -1;
            if (optionalInteger.isPresent()) {
                availableServerOccupation = optionalInteger.getAsInt();
            }
            int j = 0;
            for (int o : serverOccupations) {
                if (o == availableServerOccupation) {
                    break;
                }
                j++;
            }
            int availableServer = servers[j];
            serverOccupations[j] = requestTotalTime;
            assignedServers.add(availableServer);
        });
        return assignedServers;
    }
}
