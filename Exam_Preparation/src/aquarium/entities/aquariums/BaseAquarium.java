package aquarium.entities.aquariums;

import aquarium.entities.decorations.Decoration;
import aquarium.entities.fish.Fish;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static aquarium.common.ConstantMessages.WATER_NOT_SUITABLE;

public abstract class BaseAquarium implements Aquarium {
    private String name;
    private int capacity;
    private List<Decoration> decorations;
    private List<Fish> fish;

    protected BaseAquarium(String name, int capacity) {
        setName(name);
        this.capacity = capacity;
        this.decorations = new ArrayList<>();
        this.fish = new ArrayList<>();
    }


    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new NullPointerException("Aquarium name cannot be null or empty.");
        }
        this.name = name;
    }

    @Override
    public int calculateComfort() {
        return decorations.stream().mapToInt(Decoration::getComfort).sum();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void addFish(Fish fish) {
        if (this.fish.size() >= capacity) {
            throw new IllegalStateException("Not enough capacity.");
        }

        String fishWaterType = fish.getClass().getSimpleName().replaceAll("Fish", "");

        if (!this.getClass().getSimpleName().contains(fishWaterType)) {
            throw new IllegalStateException(WATER_NOT_SUITABLE);
        }

        this.fish.add(fish);
    }

    @Override
    public void removeFish(Fish fish) {
        this.getFish().remove(fish);
    }

    @Override
    public void addDecoration(Decoration decoration) {
        this.getDecorations().add(decoration);
    }

    @Override
    public void feed() {
        for (Fish f : fish) {
            f.eat();
        }
    }

    @Override
    public String getInfo() {

        String fishString = fish.isEmpty() ? "none" : fish.stream().map(Fish::getName).collect(Collectors.joining(" "));

        return String.format("%s (%s):%n" +
                "Fish: %s%n" +
                "Decorations: %d%n" +
                "Comfort: %d", name, this.getClass().getSimpleName(), fishString, decorations.size(), calculateComfort());

    }


    @Override
    public Collection<Fish> getFish() {
        return this.fish;
    }

    @Override
    public Collection<Decoration> getDecorations() {
        return this.decorations;
    }
}
