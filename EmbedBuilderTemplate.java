package se.dessischment.templates;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class EmbedBuilderTemplate extends ListenerAdapter {
    
    public static EmbedBuilder builder;
    
    public static EmbedBuilder simpleEmbedBuilderTemplate(String title, String footer, String description, String author, String image, String thumbnail, String color) {
        builder = new EmbedBuilder();
        
        builder
                .setTitle(title)
                .setFooter(footer)
                .setDescription(description)
                .setAuthor(author)
                .setColor(Color.GREEN);
        return  builder;
    }
    
}
