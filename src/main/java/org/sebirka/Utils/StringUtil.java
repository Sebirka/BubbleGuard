package org.sebirka.Utils;

import java.util.HashMap;
import java.util.Map;

public class StringUtil {
    public static final Map<String, String> replacements = new HashMap();

    public StringUtil() {
        this.addReplacements(new String[]{"а", "е", "к", "о", "т"}, new String[]{"a", "e", "k", "o", "t"});
        this.addReplacements(new String[]{"ａ", "ｂ", "ｃ", "ｄ", "ｅ", "ｆ", "ｇ", "ｈ", "ｉ", "ｊ", "ｋ", "ｌ", "ｍ", "ｎ", "ｏ", "ｐ", "ｑ", "ｒ", "ｓ", "ｔ", "ｕ", "ｖ", "ｗ", "ｘ", "ｙ", "ｚ"}, new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"});
        this.addReplacements(new String[]{"Ａ", "Ｂ", "Ｃ", "Ｄ", "Ｅ", "Ｆ", "Ｇ", "Ｈ", "Ｉ", "Ｊ", "Ｋ", "Ｌ", "Ｍ", "Ｎ", "Ｏ", "Ｐ", "Ｑ", "Ｒ", "Ｓ", "Ｔ", "Ｕ", "Ｖ", "Ｗ", "Ｘ", "Ｙ", "Ｚ"}, new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"});
        this.addReplacements(new String[]{"\ud835\udc1a", "\ud835\udc1b", "\ud835\udc1c", "\ud835\udc1d", "\ud835\udc1e", "\ud835\udc1f", "\ud835\udc20", "\ud835\udc21", "\ud835\udc22", "\ud835\udc23", "\ud835\udc24", "\ud835\udc25", "\ud835\udc26", "\ud835\udc27", "\ud835\udc28", "\ud835\udc29", "\ud835\udc2a", "\ud835\udc2b", "\ud835\udc2c", "\ud835\udc2d", "\ud835\udc2e", "\ud835\udc2f", "\ud835\udc30", "\ud835\udc31", "\ud835\udc32", "\ud835\udc33"}, new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"});
        this.addReplacements(new String[]{"\ud835\udc00", "\ud835\udc01", "\ud835\udc02", "\ud835\udc03", "\ud835\udc04", "\ud835\udc05", "\ud835\udc06", "\ud835\udc07", "\ud835\udc08", "\ud835\udc09", "\ud835\udc0a", "\ud835\udc0b", "\ud835\udc0c", "\ud835\udc0d", "\ud835\udc0e", "\ud835\udc0f", "\ud835\udc10", "\ud835\udc11", "\ud835\udc12", "\ud835\udc13", "\ud835\udc14", "\ud835\udc15", "\ud835\udc16", "\ud835\udc17", "\ud835\udc18", "\ud835\udc19"}, new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"});
        this.addReplacements(new String[]{"\ud835\udd52", "\ud835\udd53", "\ud835\udd54", "\ud835\udd55", "\ud835\udd56", "\ud835\udd57", "\ud835\udd58", "\ud835\udd59", "\ud835\udd5a", "\ud835\udd5b", "\ud835\udd5c", "\ud835\udd5d", "\ud835\udd5e", "\ud835\udd5f", "\ud835\udd60", "\ud835\udd61", "\ud835\udd62", "\ud835\udd63", "\ud835\udd64", "\ud835\udd65", "\ud835\udd66", "\ud835\udd67", "\ud835\udd68", "\ud835\udd69", "\ud835\udd6a", "\ud835\udd6b"}, new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"});
        this.addReplacements(new String[]{"\ud835\ude8a", "\ud835\ude8b", "\ud835\ude8c", "\ud835\ude8d", "\ud835\ude8e", "\ud835\ude8f", "\ud835\ude90", "\ud835\ude91", "\ud835\ude92", "\ud835\ude93", "\ud835\ude94", "\ud835\ude95", "\ud835\ude96", "\ud835\ude97", "\ud835\ude98", "\ud835\ude99", "\ud835\ude9a", "\ud835\ude9b", "\ud835\ude9c", "\ud835\ude9d", "\ud835\ude9e", "\ud835\ude9f", "\ud835\udea0", "\ud835\udea1", "\ud835\udea2", "\ud835\udea3"}, new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"});
        this.addReplacements(new String[]{"\ud835\udd38", "\ud835\udd39", "ℂ", "\ud835\udd3b", "\ud835\udd3c", "\ud835\udd3d", "\ud835\udd3e", "ℍ", "\ud835\udd40", "\ud835\udd41", "\ud835\udd42", "\ud835\udd43", "\ud835\udd44", "ℕ", "\ud835\udd46", "ℙ", "ℚ", "ℝ", "\ud835\udd4a", "\ud835\udd4b", "\ud835\udd4c", "\ud835\udd4d", "\ud835\udd4e", "\ud835\udd4f", "\ud835\udd50", "ℤ"}, new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"});
        this.addReplacements(new String[]{"\ud835\ude70", "\ud835\ude71", "\ud835\ude72", "\ud835\ude73", "\ud835\ude74", "\ud835\ude75", "\ud835\ude76", "\ud835\ude77", "\ud835\ude78", "\ud835\ude79", "\ud835\ude7a", "\ud835\ude7b", "\ud835\ude7c", "\ud835\ude7d", "\ud835\ude7e", "\ud835\ude7f", "\ud835\ude80", "\ud835\ude81", "\ud835\ude82", "\ud835\ude83", "\ud835\ude84", "\ud835\ude85", "\ud835\ude86", "\ud835\ude87", "\ud835\ude88", "\ud835\ude89"}, new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"});
        this.addReplacements(new String[]{"ᴀ", "ʙ", "ᴄ", "ᴅ", "ᴇ", "ꜰ", "ɢ", "ʜ", "ɪ", "ᴊ", "ᴋ", "ʟ", "ᴍ", "ɴ", "ᴏ", "ᴘ", "ǫ", "ʀ", "s", "ᴛ", "ᴜ", "ᴠ", "ᴡ", "x", "ʏ", "ᴢ"}, new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"});
    }

    private void addReplacements(String[] source, String[] target) {
        if (source.length != target.length) {
            throw new IllegalArgumentException("Source and target arrays must have the same length.");
        } else {
            for(int i = 0; i < source.length; ++i) {
                this.replacements.put(source[i], target[i]);
            }

        }
    }
}
