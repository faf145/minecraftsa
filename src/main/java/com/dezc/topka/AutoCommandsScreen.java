package com.dezc.topka;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import java.util.*;

public class AutoCommandsScreen extends Screen {
    private final List<TextFieldWidget> cmdFields   = new ArrayList<>();
    private final List<TextFieldWidget> delayFields = new ArrayList<>();
    private final List<CheckboxWidget>  checks      = new ArrayList<>();

    public AutoCommandsScreen() { super(Text.literal("TopkaAutoCommands")); }

    @Override
    protected void init() {
        cmdFields.clear(); delayFields.clear(); checks.clear();
        for (int i = 0; i < AutoCommandsManager.COUNT; i++) {
            AutoCommandsManager.CommandEntry cmd = AutoCommandsManager.commands[i];
            int y = 40 + i * 26;

            CheckboxWidget cb = CheckboxWidget.builder(Text.literal(""), textRenderer)
                .pos(8, y + 2).checked(cmd.enabled).build();
            addDrawableChild(cb);
            checks.add(cb);

            TextFieldWidget tf = new TextFieldWidget(textRenderer, 30, y, width - 90, 20, Text.literal(""));
            tf.setText(cmd.command);
            tf.setMaxLength(256);
            addDrawableChild(tf);
            cmdFields.add(tf);

            TextFieldWidget df = new TextFieldWidget(textRenderer, width - 55, y, 45, 20, Text.literal(""));
            df.setText(String.valueOf(cmd.delay));
            df.setMaxLength(3);
            addDrawableChild(df);
            delayFields.add(df);
        }
        addDrawableChild(ButtonWidget.builder(Text.literal("Сохранить"), b -> { save(); close(); })
            .dimensions(width / 2 - 50, height - 28, 100, 20).build());
    }

    private void save() {
        for (int i = 0; i < AutoCommandsManager.COUNT; i++) {
            AutoCommandsManager.commands[i].enabled = checks.get(i).isChecked();
            AutoCommandsManager.commands[i].command = cmdFields.get(i).getText();
            try { AutoCommandsManager.commands[i].delay = Integer.parseInt(delayFields.get(i).getText()); }
            catch (NumberFormatException e) { AutoCommandsManager.commands[i].delay = 5; }
            AutoCommandsManager.restartScheduler(i);
        }
        ConfigManager.save();
    }

    @Override
    public void render(DrawContext ctx, int mx, int my, float delta) {
        renderBackground(ctx, mx, my, delta);
        ctx.drawCenteredTextWithShadow(textRenderer, title, width / 2, 12, 0xFFFFFF);
        ctx.drawTextWithShadow(textRenderer, Text.literal("ON  Команда"), 8, 28, 0xAAAAAA);
        ctx.drawTextWithShadow(textRenderer, Text.literal("Мин"), width - 55, 28, 0xAAAAAA);
        super.render(ctx, mx, my, delta);
    }

    @Override public boolean shouldPause() { return false; }
}
