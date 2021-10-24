package com.anish.screen;
//branch maze
import java.awt.Color;
import java.awt.event.KeyEvent;

import java.util.ArrayList;

import com.anish.calabashbros.Calabash;
import com.anish.calabashbros.World;
import com.anish.calabashbros.Wall;
import com.anish.calabashbros.Floor;
import com.anish.calabashbros.Destination;

import asciiPanel.AsciiPanel;

import com.anish.mazeGenerator.MazeGenerator;

public class WorldScreen implements Screen {

    private World world;
    private Calabash calabash;
    private Destination destination;
    private int[][] maze;
    private boolean[][] seen;
    private int[][] directions;
    private ArrayList<int[]> path;
    private ArrayList<int[]> tpath;

    private int mazeBegin = 10;
    private int mazeSize = 30;
    private int tx = 0, ty = 0;
    private int step = 0;

    public WorldScreen() {
        world = new World();

        MazeGenerator mazeGenerator = new MazeGenerator(mazeSize);
        mazeGenerator.generateMaze();
        maze = mazeGenerator.getMaze();

        for (int i = -1; i <= mazeSize; i++) {
            for (int j = -1; j <= mazeSize; j++) {
                if (i < 0 || i >= mazeSize || j < 0 || j >= mazeSize || maze[i][j] == 0) { //wall
                    Wall wall = new Wall(world);
                    world.put(wall, mazeBegin + i, mazeBegin + j);
                }
                else { //floor
                    Floor floor = new Floor(world);
                    world.put(floor, mazeBegin + i, mazeBegin + j);
                }
            }
        }
        calabash = new Calabash(new Color(204, 0, 0), 0, world);
        world.put(calabash, mazeBegin, mazeBegin);
        destination = new Destination(world);
        world.put(destination, mazeBegin + mazeSize - 1, mazeBegin + mazeSize - 1);

        directions = new int[4][2];
        directions[0][0] = -1; directions[0][1] = 0; //left
        directions[1][0] = 0; directions[1][1] = -1; //up
        directions[2][0] = 1; directions[2][1] = 0; //right
        directions[3][0] = 0; directions[3][1] = 1; //down

        path = new ArrayList<>();
        tpath = new ArrayList<>();
    }

    private void dfs(int x, int y) {
        seen[x][y] = true;
        if (x == mazeSize - 1 && y == mazeSize - 1) {
            for (int i = 0; i < tpath.size(); i++) {
                path.add(tpath.get(i));
            }
        }
        for (int i = 0; i < 4; i++) {
            int nextx = x + directions[i][0];
            int nexty = y + directions[i][1];
            if (nextx < 0 || nextx >= mazeSize) {
                continue;
            }
            else if (nexty < 0 || nexty >= mazeSize) {
                continue;
            }
            else if (maze[nextx][nexty] == 0 || seen[nextx][nexty]) {
                continue;
            }
            tpath.add(new int[]{nextx, nexty});
            dfs(nextx, nexty);
            tpath.remove(tpath.size() - 1);
        }

    }

    @Override
    public void displayOutput(AsciiPanel terminal) {

        for (int x = 0; x < World.WIDTH; x++) {
            for (int y = 0; y < World.HEIGHT; y++) {

                terminal.write(world.get(x, y).getGlyph(), x, y, world.get(x, y).getColor());

            }
        }
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        if (tx == mazeSize - 1 && ty == mazeSize - 1) {
            return this;
        }
        int keyCode = key.getKeyCode();
        if (keyCode < KeyEvent.VK_LEFT || keyCode > KeyEvent.VK_DOWN) { //dfs
            if (path.size() == 0) {
                path = new ArrayList<>();
                tpath = new ArrayList<>();
                seen = new boolean[mazeSize][mazeSize];
                dfs(tx, ty);
                if (path.size() == 0) {
                    System.out.println("This maze doesn't have a solution!\n");
                    return this;
                }
                step = 0;
            }

            int nx = path.get(step)[0];
            int ny = path.get(step)[1];
            step++;
            Floor floor = new Floor(world);
            world.put(floor, mazeBegin + tx, mazeBegin + ty);
            calabash.moveTo(mazeBegin + nx, mazeBegin + ny);
            tx = nx; ty = ny;
        }
        else { //step
            int nx = tx + directions[keyCode - KeyEvent.VK_LEFT][0];
            int ny = ty + directions[keyCode - KeyEvent.VK_LEFT][1];
            if (nx < 0 || nx >= mazeSize) {
                return this;
            }
            else if (ny < 0 || ny >= mazeSize) {
                return this;
            }
            else if (maze[nx][ny] == 0) {
                return this;
            }
            Floor floor = new Floor(world);
            world.put(floor, mazeBegin + tx, mazeBegin + ty);
            calabash.moveTo(mazeBegin + nx, mazeBegin + ny);
            tx = nx; ty = ny;

            path = new ArrayList<>();
            tpath = new ArrayList<>();
        }

        return this;
    }

}
