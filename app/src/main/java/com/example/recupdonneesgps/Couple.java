package com.example.recupdonneesgps;

public class Couple<F, S> {
        private F first; //first member of pair
        private S second; //second member of pair

    public Couple(F first, S second) {
        this.first = first;
        this.second = second;
    }

        public void setFirst(F first) {
        this.first = first;
    }

        public void setSecond(S second) {
        this.second = second;
    }

        public F getFirst() {
        return first;
    }

        public S getSecond() {
        return second;
    }

    public String toString() {
        return "id : "+first.toString() + " Nom voiture : " + second.toString();
    }

}
