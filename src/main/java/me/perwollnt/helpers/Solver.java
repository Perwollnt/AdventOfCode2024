package me.perwollnt.helpers;

public abstract class Solver {
    public abstract void solve1();

    public abstract void solve2();

    public abstract String print();

    public abstract int getDay();

    public void solve() {
        solve1();
        solve2();
    }

    public boolean isDisabled() {
        return true;
    }
}
