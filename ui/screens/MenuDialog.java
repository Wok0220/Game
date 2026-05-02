package com.ok.ui.screens;

import com.ok.resource.ResourceManager;
import com.ok.ui.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * 菜单对话框
 */
public class MenuDialog extends JDialog {

    /** 游戏界面 */
    private GameScreen gameScreen;

    /** 主窗口 */
    private GameFrame gameFrame;

    /** 背景图片 */
    private BufferedImage backgroundImage;

    /** 返回按钮图片 */
    private BufferedImage backImage;

    /** 退出按钮图片 */
    private BufferedImage quitImage;

    /** 返回按钮区域 */
    private Rectangle backRect;

    /** 退出按钮区域 */
    private Rectangle quitRect;

    /**
     * 构造函数
     * @param gameFrame 主窗口
     * @param gameScreen 游戏界面
     */
    public MenuDialog(GameFrame gameFrame, GameScreen gameScreen) {
        super(gameFrame, "菜单", true);
        this.gameFrame = gameFrame;
        this.gameScreen = gameScreen;

        // 只占一部分（600x400 像素）
        int dialogWidth = 300;
        int dialogHeight = 500;
        setSize(dialogWidth, dialogHeight);
        setUndecorated(true);
        setLocationRelativeTo(gameFrame);

        setBackground(new Color(0, 0, 0, 0));
        setOpacity(1.0f);

        // 加载图片
        backgroundImage = ResourceManager.getInstance().getBackgroundImage("菜单");
        backImage = ResourceManager.getInstance().getUIImage("back");
        quitImage = ResourceManager.getInstance().getUIImage("quit");

        // 初始化按钮区域（根据对话框大小重新计算）
        int buttonWidth = 120;
        int buttonHeight = 55;
        int centerX = (dialogWidth - buttonWidth) / 2;
        int bottomY = dialogHeight - 80;

        backRect = new Rectangle(centerX - 25, bottomY - 10, buttonWidth + 60, buttonHeight + 25);
        quitRect = new Rectangle(centerX, bottomY - 100, buttonWidth, buttonHeight);

        // 添加鼠标监听器
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e.getX(), e.getY());
            }
        });

        // 创建面板
        MenuPanel panel = new MenuPanel();
        panel.setOpaque(false);
        setContentPane(panel);
    }

    /**
     * 处理鼠标点击
     */
    private void handleMouseClick(int x, int y) {
        if (backRect.contains(x, y)) {
            // 返回游戏
            dispose();
        } else if (quitRect.contains(x, y)) {
            // 返回主菜单
            dispose();
            gameFrame.showMainScreen();
        }
    }

    /**
     * 菜单面板
     */
    private class MenuPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // 显示背景图片（缩放到对话框大小）
            if (backgroundImage != null) {
                g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
            }

            // 显示返回按钮
            if (backImage != null) {
                g2d.drawImage(backImage, backRect.x, backRect.y,
                        backRect.width, backRect.height, null);
            }

            // 显示退出按钮
            if (quitImage != null) {
                g2d.drawImage(quitImage, quitRect.x, quitRect.y,
                        quitRect.width, quitRect.height, null);
            }
        }
    }
}