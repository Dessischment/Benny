package se.dessischment.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import se.dessischment.Main;
import se.dessischment.templates.EmbedBuilderTemplate;

import java.util.concurrent.TimeUnit;

public class BanCommand extends ListenerAdapter {

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if (event.getName().startsWith("ban")) {

            if (event.getMember().hasPermission(Permission.ADMINISTRATOR) || event.getMember().hasPermission(Permission.BAN_MEMBERS)) {
                Member user = event.getOption("user").getAsMember();
                String reason = event.getOption("reason").getAsString();
                int deldays = event.getOption("deldays").getAsInt();

                user.ban(deldays, TimeUnit.DAYS).reason(reason).queue();

                EmbedBuilder embed = EmbedBuilderTemplate.simpleEmbedBuilderTemplate("Succssed :white_check_mark:", event.getGuild().getName(), "The User: " + user.getUser().getName() + " is now arrestet!", "", "", "", "");
                event.getChannel().sendMessageEmbeds(embed.build()).queue();
            }
            else {
                event.reply("You do not have the Permission to ban a Member!").setEphemeral(true).queue();
            }

        }

    }

}
