package com.dezc.betterfps;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class AutoCommandsScreen extends Screen {

    private List<TextFieldWidget> commandFields = new ArrayList<>();
    private List<TextFieldWidget> delayFields = new ArrayList<>();
    private List<CheckboxWidget> checkboxes = new ArrayList<>();

    public AutoCommandsScreen() {
        super(Text.literal("TopkaAutoCommands"));
    }

    @Override
    protected void init() {
        commandFields.clear();
        delayFields.clear();
        checkboxes.clear();

        int startY = 30;
        for (int i = 0; i < 5; i++) {
            AutoCommandsManager.CommandEntry cmd = AutoCommandsManager.commands[i];
            int y = startY + i * 28;

            CheckboxWidget cb = CheckboxWidget.builder(Text.literal(""), textRenderer)
                .pos(10, y + 2)
                .checked(cmd.enabled)
                .build();
            addDrawableChild(cb);
            checkboxes.add(cb);

            TextFieldWidget cmdField = new TextFieldWidget(textRenderer, 35, y, 200, 20, Text.literal(""));
            cmdField.setText(cmd.command);
            cmdField.setMaxLength(256);
            addDrawableChild(cmdField);
            commandFields.add(cmdField);

            TextFieldWidget delayField = new TextFieldWidget(textRenderer, 245, y, 40, 20, Text.literal(""));
            delayField.setText(String.valueOf(cmd.delay));
            delayField.setMaxLength(4);
            addDrawableChild(delayField);
            delayFields.add(delayField);
        }

        addDrawableChild(ButtonWidget.builder(Text.literal("Сохранить"), btn -> {
            save();
            close();
        }).dimensions(width / 2 - 50, height - 30, 100, 20).build());
    }

    private void save() {
        for (int i = 0; i < 5; i++) {
            AutoCommandsManager.commands[i].enabled = checkboxes.get(i).isChecked();
            AutoCommandsManager.commands[i].command = commandFields.get(i).getText();
            try {
                AutoCommandsManager.commands[i].delay = Integer.parseInt(delayFields.get(i).getText());
            } catch (NumberFormatException e) {
                AutoCommandsManager.commands[i].delay = 5;
            }
            AutoCommandsManager.restartScheduler(i);
        }
        ConfigManager.save();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(textRenderer, title, width / 2, 10, 0xFFFFFF);
        context.drawTextWithShadow(textRenderer, Text.literal("Команда"), 35, 18, 0xAAAAAA);
        context.drawTextWithShadow(textRenderer, Text.literal("Мин"), 245, 18, 0xAAAAAA);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
