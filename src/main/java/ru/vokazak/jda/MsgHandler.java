package ru.vokazak.jda;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class MsgHandler extends ListenerAdapter {

    public static final String invitation = "Минтимер Шаймиев\n" +
            "Рудольф Нуриев\n" +
            "Губайдулина София\n" +
            "Ренат Ибрагимов\n" +
            "Алсу\n" +
            "Марат\n" +
            "Сафиннар\n" +
            "Дасаев Ринат\n" +
            "\n" +
            "Хаматова Чулпан\n" +
            "\n" +
            "Бер, ике, эч, дурт\n" +
            "Татарстан Super good!\n" +
            "Биш, алтэ, жиде, сигез\n" +
            "Рахим итегез\n";

    public MsgHandler() {

    }

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        String receivedMsg = e.getMessage().getContentRaw();

        if (receivedMsg.contains("Дмитрий")) {
            e.getChannel().sendMessage("Ты лабы сделал?").queue();
        }

        if (receivedMsg.contains("Таир, зови")) {
            sendInvitation(e.getChannel());
        }

    }

    public static void sendInvitation(MessageChannel channel) {
        channel.sendMessage("\n@everyone\n Деееети, заходите").queue();
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Дети, заходите");
        eb.setColor(Color.RED);
        eb.setImage("https://media.discordapp.net/attachments/773896116356775937/783357118157553674/dmZo-bEgU0M.png?width=480&height=521");
        eb.setTitle(invitation);
        channel.sendMessage(eb.build()).queue();
    }

}
