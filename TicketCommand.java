package se.dessischment.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import se.dessischment.templates.EmbedBuilderTemplate;

import java.util.EnumSet;
import java.util.List;

public class TicketCommand extends ListenerAdapter {

    public int i = 1;
    public Role role;

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if (event.getName().equals("setup-ticket")) {

            if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                Channel channel = event.getOption("channel").getAsChannel();
                TextChannel textChannel = event.getGuild().getTextChannelsByName(channel.getName(), false).get(0);
                role = event.getOption("role").getAsRole();
                String content = event.getOption("text").getAsString();
                boolean isBox = event.getOption("helpbox").getAsBoolean();

                EmbedBuilder embed = EmbedBuilderTemplate.simpleEmbedBuilderTemplate("Need help?", event.getGuild().getName(), content, "", "", "", "");
                Button button = Button.success("open", "Open Ticket!").withEmoji(Emoji.fromFormatted("\uD83C\uDF9F\uFE0F"));

                if (isBox) {
                    embed.addField("How to use", "Click on that button below and a ticket will open!", true);
                }

                textChannel.sendMessageEmbeds(embed.build()).setActionRow(button).queue();
            }

        }

    }

    public void onButtonInteraction(ButtonInteractionEvent event) {

        if (event.getButton().getId().equals("open")) {
            TextChannel textChannel = event.getGuild().createTextChannel("Ticket-" + i)
                    .addPermissionOverride(event.getMember(), EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND), null)
                    .addPermissionOverride(role, null, EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND))
                    .complete();
            EmbedBuilder embed = EmbedBuilderTemplate.simpleEmbedBuilderTemplate("Ticket created!", event.getGuild().getName() + " - Ticket System", "Someone will fix you Problem!", "", "", "", "");
            embed.addField("Member:", event.getMember().getUser().getName(), true);
            Button closeButton = Button.danger("closebutton", "Close Ticket").withEmoji(Emoji.fromFormatted("\uD83C\uDFAB"));
            Button takeButton = Button.primary("takeButton", "Claim Ticket").withEmoji(Emoji.fromFormatted("\uD83D\uDEE1\uFE0F"));

            textChannel.sendMessageEmbeds(embed.build()).setActionRow(closeButton, takeButton);
            event.reply(":white_check_mark: Succeed!").setEphemeral(true).queue();

            i++;
        }

        if (event.getButton().getId().equals("closebutton")) {

            if (event.getMember().hasPermission(Permission.ADMINISTRATOR) || event.getMember().hasPermission(Permission.KICK_MEMBERS)) {
                event.getChannel().delete().complete();
            }

        }

        if (event.getButton().getId().equals("takebutton")) {

            if (event.getMember().hasPermission(Permission.KICK_MEMBERS) || event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                event.getGuild().getTextChannelsByName(event.getChannel().getName(), false).get(0).getManager().putPermissionOverride(event.getMember(), EnumSet.of(Permission.MANAGE_CHANNEL), null).queue();

                EmbedBuilder embed = EmbedBuilderTemplate.simpleEmbedBuilderTemplate("Teammember took the Ticket over!", event.getGuild().getName(), " - Ticket System", "", "", "", "");
                embed.addField("Teammeber:", event.getMember().getUser().getName(), true);

                event.getChannel().sendMessageEmbeds(embed.build()).queue();
                event.reply("You claimed the Ticket!").setEphemeral(true).queue();
            }

        }

    }

}
