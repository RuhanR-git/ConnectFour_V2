import java.util.ArrayList;
import java.util.HashMap;

public class Server 
{
    private final HashMap<String, Integer> userCredentials;
    private final ArrayList<User> userList;

    public Server() {
        userCredentials = new HashMap<>();
        userList = new ArrayList<>();
    }

    public void addUserMap(User user) 
    {
        userCredentials.put(user.getName(), user.getPassword());
    }

    public void addUserArray(User user)
    {
        userList.add(user);
    }

    public String leaderboard()
    {
        String x = "";
        for(User e : userList)
        {
            x += e.getName() + " " + e.getWins() + "\n";
        }
        return x;
    }

    public int findMaxIndex(int min, int max)
    {
        int maxIndex = min;
        for (int i = min; i < userList.size(); i++) 
        {
            if(userList.get(i).getWins() > userList.get(maxIndex).getWins())
            {
                maxIndex = i;
            }    
        }
        return maxIndex;
    }

    public void swap(int a, int b)
    {
        User temp;
        temp = userList.get(a);
        userList.set(a, userList.get(b));
        userList.set(b, temp);
    }

    public void sort()
    {
        for(int i = 0; i < userList.size(); i++)
        {
            int maxIndex = findMaxIndex(i, userList.size());
            if(userList.get(i).getWins() != userList.get(maxIndex).getWins())
            {
                swap(i, maxIndex);
            }
        }
    }

    public User validateLogin(String username, int password) {
        if (userCredentials.containsKey(username) && userCredentials.get(username) == password) {
            return new User(username, password); // Create a new User object for simplicity
        }
        return null;
    }

    public void updateUser(User updatedUser) {
        // Update the user in the map
        userCredentials.put(updatedUser.getName(), updatedUser.getPassword());
        // Update the user in the array/list
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getName().equals(updatedUser.getName())) {
                userList.set(i, updatedUser);
                break;
            }
        }
    }
}
