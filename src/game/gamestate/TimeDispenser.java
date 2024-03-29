package game.gamestate;

import commands.DispenseCommand;
import game.Game;
import game.objects.Sliceable;
import game.objects.SliceableType;


public class TimeDispenser extends GameState {
    private int dispensed = 0;
    public TimeDispenser(int maxInterval) {
        super(maxInterval);
    }

    @Override
    DispenseCommand nextDispense() {
        double fatalChance = 0.05, special1Chance = 0.05, special2Chance = 0.03;
        double r = random.nextDouble();
        double p = fatalChance;
        if(dispensed++ >= 25) {
            stop();
            int maxInterval = this.maxInterval - 150;
            if(maxInterval < 650)
                Game.getCurrentGame().setState(new FinalTimeDispenser(650));
            else
                Game.getCurrentGame().setState(new TimeDispenser(maxInterval));
        }
        if(r<p) {
            delay = random.nextInt(maxInterval);
            return new DispenseCommand(Sliceable.newSliceable(SliceableType.FATAL_BOMB));
        }
        else if(r<(p+=special1Chance)) {
            delay = random.nextInt(maxInterval);
            return new DispenseCommand(Sliceable.newSliceable(SliceableType.SPECIAL_1));
        }
        else if(r<(p+=special2Chance)) {
            delay = random.nextInt(maxInterval);
            return new DispenseCommand(Sliceable.newSliceable(SliceableType.SPECIAL_2));
        }
        else {
            SliceableType type = SliceableType.class.getEnumConstants()[random.nextInt(6)];
            delay = random.nextInt(maxInterval);
            return new DispenseCommand(Sliceable.newSliceable(type));
        }
    }
}
