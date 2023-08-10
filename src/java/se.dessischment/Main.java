package se.dessischment;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import se.dessischment.commands.BanCommand;
import se.dessischment.commands.TicketCommand;
import se.dessischment.commands.VerifyCommands;

public class Main {

    public static JDA jda;
    public static String PREFIX = "!";

    public static void main(String[] args) throws InterruptedException {
        String token = "MTA5MzExNDM5NDMzMTg2MTAwMg.GutRDh.osJ1WtxFBWUA27Kr8CXLM7VtXOp-f4UVqIi_es";

        jda = JDABuilder.createDefault(token)
                .setStatus(OnlineStatus.ONLINE)
                .setActivity(Activity.listening("Hvem Stjal Spenolen"))
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableIntents(
                        GatewayIntent.GUILD_MEMBERS,
                        GatewayIntent.GUILD_PRESENCES,
                        GatewayIntent.GUILD_VOICE_STATES,
                        GatewayIntent.DIRECT_MESSAGE_TYPING,
                        GatewayIntent.DIRECT_MESSAGES,
                        GatewayIntent.MESSAGE_CONTENT,
                        GatewayIntent.GUILD_INVITES
                )
                .addEventListeners(
                    new VerifyCommands(),
                    new BanCommand(),
                    new TicketCommand()
                )
                .build().awaitReady();

        jda.updateCommands().addCommands(
                Commands.slash("ban", "Ban a user from your Server!")
                        .addOption(OptionType.USER, "user", "The User I should ban for you")
                        .addOption(OptionType.STRING, "reason", "Tell me about the reasons!")
                        .addOption(OptionType.INTEGER, "deldays", "Numbers of Messages I should delete from the user"),
                Commands.slash("verify", "Verify Textbox for Server Moderation!")
                        .addOption(OptionType.CHANNEL, "channel", "In which channel should be the embed?")
                        .addOption(OptionType.ROLE, "role", "What is the 'verify Role'?")
                        .addOption(OptionType.STRING, "text", "Say hello to ur Server!")
                        .addOption(OptionType.BOOLEAN, "helpbox", "You Members may not know, what a verify box is!"),
                Commands.slash("setup-ticket", "Every good Server needs a Ticket System!")
                        .addOption(OptionType.CHANNEL, "channel", "The channel a Ticket Embed should pop up.")
                        .addOption(OptionType.ROLE, "role", "Team Role!")
                        .addOption(OptionType.STRING, "text", "The text shown up in the Embed.")
                        .addOption(OptionType.BOOLEAN, "helpbox", "Your Members may not know what a Ticket is!")
        ).queue();

        System.out.println("[Beny] Jag är Online!");
    }

}
