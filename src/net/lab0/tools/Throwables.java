package net.lab0.tools;

import java.util.ArrayList;
import java.util.List;

public class Throwables
{
    public static List<String> getMessages(Throwable throwable)
    {
        Throwable t = throwable;
        List<String> messages = new ArrayList<>();
        do
        {
            String message = throwable.getMessage();
            if (!"".equals(message))
            {
                messages.add(throwable.getMessage());
            }
            t = t.getCause();
        } while (t != null);
        return messages;
    }
}
