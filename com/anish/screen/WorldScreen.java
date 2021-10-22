package com.anish.screen;
//branch matrix
import java.awt.Color;
import java.awt.event.KeyEvent;

import com.anish.calabashbros.BubbleSorter;
import com.anish.calabashbros.Calabash;
import com.anish.calabashbros.World;

import java.util.Collections;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import asciiPanel.AsciiPanel;

public class WorldScreen implements Screen {

    private World world;
    private Calabash[][] bros;
    String[] sortSteps;

    public WorldScreen() {
        world = new World();

        createCalabashes();
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                world.put(bros[i][j], 10 + i * 2, 10 + j * 2);
            }
        }

        BubbleSorter<Calabash> b = new BubbleSorter<>();
        b.load(toArray(bros));
        b.sort();

        sortSteps = this.parsePlan(b.getPlan());
    }

    private ArrayList<Integer> createOrder() {
        ArrayList<Integer> li = new ArrayList<>();
        for (int i = 0; i < 256; i++) {
            li.add(i);
        }
        Collections.shuffle(li);
        return li;
    }

    private int[][] createColors() {
        int[][] res = new int[16][16];
        int i = 0, j = 0;
        HashSet<Integer> s = new HashSet<>();
        Random r = new Random();
        while (i < 16) {
            int t = r.nextInt(1 << 24);
            if (s.contains(t)) {
                continue;
            }
            s.add(t);
            res[i][j] = t;
            j++;
            if (j == 16) {
                i++;
                j = 0;
            }
        }
        return res;
    }

    private void createCalabashes() {
        int[][] colors = createColors();
        ArrayList<Integer> order = createOrder();
        bros = new Calabash[16][16];
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                int r = colors[i][j] & 0xFF;
                int g = (colors[i][j] & 0xFF00) >> 8;
                int b = (colors[i][j] & 0xFF0000) >> 16;
                bros[i][j] = new Calabash(new Color(r, g, b), order.get(i * 16 + j), world);
            }
        }
    }

    private Calabash[] toArray(Calabash[][] arr) {
        Calabash[] res = new Calabash[arr.length * arr.length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                res[i * arr.length + j] = arr[i][j];
            }
        }
        return res;
    }

    private String[] parsePlan(String plan) {
        return plan.split("\n");
    }

    private void execute(Calabash[][] bros, String step) {
        String[] couple = step.split("<->");
        getBroByRank(bros, Integer.parseInt(couple[0])).swap(getBroByRank(bros, Integer.parseInt(couple[1])));
    }

    private Calabash getBroByRank(Calabash[][] bros, int rank) {
        for (int i = 0; i < bros.length; i++) {
            for (int j = 0; j < bros.length; j++) {
                if (bros[i][j].getRank() == rank) {
                    return bros[i][j];
                }
            }
        }
        return null;
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {

        for (int x = 0; x < World.WIDTH; x++) {
            for (int y = 0; y < World.HEIGHT; y++) {

                terminal.write(world.get(x, y).getGlyph(), x, y, world.get(x, y).getColor());

            }
        }
    }

    int i = 0;

    @Override
    public Screen respondToUserInput(KeyEvent key) {

        if (i < this.sortSteps.length) {
            this.execute(bros, sortSteps[i]);
            i++;
        }

        return this;
    }

}
