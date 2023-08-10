package se.dessischment.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import se.dessischment.Main;
import se.dessischment.templates.EmbedBuilderTemplate;

import java.awt.*;

public class VerifyCommands extends ListenerAdapter {

    public Role role;

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if (event.getName().startsWith("verify")) {

            if (event.getMember().hasPermission(Permission.MANAGE_SERVER) || event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                Channel channel = event.getOption("channel").getAsChannel();
                role = event.getOption("role").getAsRole();
                String content = event.getOption("text").getAsString();
                boolean isField = event.getOption("helpbox").getAsBoolean();
                TextChannel textChannel = event.getGuild().getTextChannelsByName(channel.getName(), true).get(0);

                EmbedBuilder embed = EmbedBuilderTemplate.simpleEmbedBuilderTemplate("Verify!", event.getGuild().getName(), content, "", "", "", "#00ff00");

                if (isField) {
                    embed.addField("How to use?", "Click on that button below!", true);
                }

                Button button = Button.success("verify", "Verify now!").withEmoji(Emoji.fromFormatted("✅"));

                textChannel.sendMessageEmbeds(embed.build()).setActionRow(button).queue();
                event.reply(":white_check_mark: Succeed!").setEphemeral(true).queue();
            }

        }

    }

    public void onButtonInteraction(ButtonInteractionEvent event) {

        if (event.getButton().getId().equals("verify")) {
            Role verifyRole = event.getGuild().getRoleById(role.getId());

            event.getGuild().addRoleToMember(event.getMember(), verifyRole).queue();

            event.reply("You are Verified!").setEphemeral(true).queue();
        }

    }
}
