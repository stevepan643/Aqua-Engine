package com.steve.platform;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFWVidMode;
import org.slf4j.Logger;

import com.steve.utils.LogUtil;

/**
 * The {@code Window} class is responsible for creating and managing a
 * window using the GLFW library. It initializes the GLFW library, sets the
 * necessary window hints for creating an OpenGL context, and provides a
 * method to check if the operating system is macOS.
 *
 * @author Steve Pan
 * @since 1.0
 */
public class Window {
    private final long window;
    private final GLFWVidMode mode;
    private final int monitorWidth;
    private final int monitorHeight;

    private boolean isFullScreen = false;
    
    private final Logger LOGGER = LogUtil.getLogger();
    /**
     * Creates a new window with the specified width, height, and title.
     *
     * @param width  the width of the window
     * @param height the height of the window
     * @param title  the title of the window
     * @since 1.0
     */
    public Window(int width, int height, String title) {
        init();

        window = glfwCreateWindow(width, height, title, NULL, NULL);
        LOGGER.debug("Created Window");

        mode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        monitorWidth = mode.width();
        monitorHeight = mode.height();

        // Center the window on the screen
        glfwSetWindowPos(window,
            (glfwGetVideoMode(glfwGetPrimaryMonitor()).width() - width) / 2,
            (glfwGetVideoMode(glfwGetPrimaryMonitor()).height() - height) / 2);

        // Check.
        if (window == NULL) {
            LOGGER.error("Failed to Create Window");
            glfwTerminate();
            System.exit(-1);
        }

        glfwMakeContextCurrent(window);
        createCapabilities();
    }
    

    /**
     * Initializes the GLFW library and sets the window hints for creating
     * an OpenGL context. This method sets the OpenGL context version
     * to 3.3 and uses the core profile. If the application is running on
     * macOS, it also sets the forward compatibility flag.
     * 
     * @since 1.0
     */
    private void init() {
        // Initialization glfw.
        LOGGER.debug("Initializing GLFW");
        glfwInit();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        // If run on macOS, need this.
        if (isMacOs()) {
            LOGGER.debug("Running on macOS");
            glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        }
    }

    /**
     * Retrieves the window handle.
     *
     * @return the window handle as a {@code long} value.
     * @since 1.0
     */
    public long get() {
        return window;
    }

    public void switchFullScreen() {
        if (isFullScreen) {
            glfwSetWindowPos(window,
                (monitorWidth - 800) / 2,
                (monitorHeight - 600) / 2);
            glfwSetWindowSize(window, 800, 600);
            isFullScreen = false;
        } else {
            glfwSetWindowPos(window, 0, 0);
            glfwSetWindowSize(window, monitorWidth, monitorHeight);
            isFullScreen = true;
        }
    }

    /**
     * Checks if the window should close.
     *
     * @return {@code true} if the window should close,
     *         {@code false} otherwise.
     * @since 1.0
     */
    public boolean isShouldClose() {
        return glfwWindowShouldClose(window);
    }

    /**
     * Swaps the front and back buffers of the specified window.
     * This method is typically used to update the display with the
     * rendered content.
     * It should be called after rendering operations are completed.
     * 
     * @since 1.0
     */
    public void swapBuffers() {
        glfwSwapBuffers(window);
    }

    /**
     * Checks if the operating system is macOS.
     *
     * @return {@code true} if the operating system is macOS,
     *         {@code false} otherwise.
     * @since 1.0
     */
    private static boolean isMacOs() {
        String osName = System.getProperty("os.name");
        return osName != null && osName.startsWith("Mac");
    }

}
