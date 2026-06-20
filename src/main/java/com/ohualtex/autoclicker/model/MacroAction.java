package com.ohualtex.autoclicker.model;

import com.ohualtex.autoclicker.i18n.Lang;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/** Zincir makrosundaki tek bir adim (tur + iki parametre). Listede toString ile gosterilir, serialize ile saklanir. */
public class MacroAction {
    public ActionType type;
    public int p1, p2;

    public MacroAction(ActionType type, int p1, int p2) { this.type = type; this.p1 = p1; this.p2 = p2; }

    public String toString() {
        switch(type) {
            case MOUSE_CLICK: return Lang.get("tnt_mouse") + ": " + (p1==InputEvent.BUTTON1_DOWN_MASK ? Lang.get("l_click") : p1==InputEvent.BUTTON3_DOWN_MASK ? Lang.get("r_click") : p1==999 ? Lang.get("d_click") : Lang.get("m_click"));
            case KEY_PRESS: return Lang.get("tnt_key") + ": " + KeyEvent.getKeyText(p1);
            case MOUSE_MOVE: return Lang.get("tnt_move") + ": X=" + p1 + ", Y=" + p2;
            case DELAY: return Lang.get("ms_delay") + " " + p1;
            default: return "?";
        }
    }

    public String serialize() { return type.name()+":"+p1+":"+p2; }

    public static MacroAction deserialize(String s) {
        String[] parts = s.split(":");
        return new MacroAction(ActionType.valueOf(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
    }
}
