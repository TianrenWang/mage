

package mage.game.events;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class DamagedEvent extends GameEvent {

    protected boolean combat;

    public DamagedEvent(EventType type, UUID targetId, UUID attackerId, UUID playerId, int amount, boolean combat) {
        super(type, targetId, null, playerId, amount, false);
        this.combat = combat;
        this.setSourceId(attackerId);
    }

    public boolean isCombatDamage() {
        return combat;
    }

    public boolean isPreventable() {
        return flag;
    }

}
