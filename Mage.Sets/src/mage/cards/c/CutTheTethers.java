package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.ManaUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class CutTheTethers extends CardImpl {

    public CutTheTethers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{U}");


        // For each Spirit, return it to its owner's hand unless that player pays {3}.
        this.getSpellAbility().addEffect(new CutTheTethersEffect());
    }

    public CutTheTethers(final CutTheTethers card) {
        super(card);
    }

    @Override
    public CutTheTethers copy() {
        return new CutTheTethers(this);
    }
}

class CutTheTethersEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Spirit creatures");

    static {
        filter.add(SubType.SPIRIT.getPredicate());
    }

    public CutTheTethersEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "For each Spirit, return it to its owner's hand unless that player pays {3}";
    }

    public CutTheTethersEffect(final CutTheTethersEffect effect) {
        super(effect);
    }

    @Override
    public CutTheTethersEffect copy() {
        return new CutTheTethersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent creature : game.getState().getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
            Player player = game.getPlayer(creature.getControllerId());
            if (player != null) {
                boolean paid = false;
                if (player.chooseUse(Outcome.Benefit, "Pay {3} to keep " + creature.getName() + " on the battlefield?", source, game)) {
                    Cost cost = ManaUtil.createManaCost(3, false);
                    paid = cost.pay(source, game, source, creature.getControllerId(), false, null);
                }
                if (!paid) {
                    creature.moveToZone(Zone.HAND, source, game, true);
                }
            }
        }
        return true;
    }
}
