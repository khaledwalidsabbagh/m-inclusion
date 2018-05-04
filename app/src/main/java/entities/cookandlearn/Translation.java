package entities.cookandlearn;

import java.util.HashMap;
import java.util.Map;

/**
 * Responsible to provide a map<swedish, arabic> translations
 */

public class Translation {
    private Map<String, String> translations = new HashMap();

    public Translation(){
        translations.put("Ingredienser", "المكونات");
        translations.put("liter", "لتر");
        translations.put("mjölk", "حليب");
        translations.put("mannagryn", "سميد");
        translations.put("socker", "سكر");
        translations.put("dl", "دسيليت عشر اللتر");
        translations.put("msk", "م.ك ( ملعقة كبيرة)");
        translations.put("rosenvatten", "ماء ورد");
        translations.put("tsk", "م.ص (ملعقة صغيرة)");
        translations.put("vaniljextrakt", "مستخلص الفانيلا");
        translations.put("g", "جرام");
        translations.put("hackade", "مطحون");
        translations.put("pistagenötter", "فستق حلبي");
        translations.put("dekoration", "تزين");
        translations.put("vispad grädde", "قشطة مخفوقه");
        translations.put("vispad", "مخفوقه");
        translations.put("grädde", "قشطة");
        translations.put("sirap", " شيرة");
        translations.put("sirap enligt smak", " شيرة حسب الرغبة");
        translations.put("Gör så här", "افعل هكذ ا(التعليمات)");
        translations.put("Värm upp", "سخّن");
        translations.put("tillsammans med", "مع بعض مع");
        translations.put("kastrull", "حلة طبخ");
        translations.put("Rör om", "حرك");
        translations.put("smälter", "يذوب");
        translations.put("Tillsätt", "اضف");
        translations.put("låg värme", "خفض الحرارة");
        translations.put("kokar", "غاز الطبخ");
        translations.put("smeten", "الخليط");
        translations.put("tjockare", " سميك  متجانس");
        translations.put("Häll", "اسكب");
        translations.put("serveringsform", "طبق التقديم");
        translations.put("ställ in", "ضع");
        translations.put("kylskåpet", "الثلاجة");
        translations.put("minst", "أقل شيء");
        translations.put("Bred ut", "اخرج");
        translations.put("enligt smak", "حسب الرغبة");
        translations.put("chokladbollar", "كرات البشوكولاتة");
        translations.put("karamellsmak", "نكهة الكراميل");
        translations.put("puttra", "ينضج");
        translations.put("Rulla", "كور");
        translations.put("föredrar", "تفضل");
        translations.put("Portioner", "حصص");
        translations.put("kort stund", "وقت قصير");
        translations.put("Rör ihop", "حرك مع بعض");
        translations.put("smeten", "الخليط");
        translations.put("stelnar", "يجمد");
        translations.put("Forma", "شكل");
        translations.put("Rulla", "كور");
        translations.put("mångfärgat", "متعدد الألوان");
        translations.put("kokos", "جوز الهند");
        translations.put("eller", "أو");
        translations.put("annan", "آخر");
        translations.put("garnering", "زينة");
        translations.put("Servera", "قدم");
        translations.put("Ställ in", "ضع");
        translations.put("kylskåp", "الثلاجة");
        translations.put("smör", "زبدة");
        translations.put("socker", "سكر");
        translations.put("vaniljsocker", "سكر الفانيلا ");
        translations.put("kakao", "كاكاو");
        translations.put("havregryn", "دقيق الشوفان");
        translations.put("kallt", "بارد");
        translations.put("starkt", "قوي");
        translations.put("kaffe", "قهوة");
        translations.put("pärlsocker", "حبيبات السكر");
    }

    public boolean translationExists(String word){
        return translations.containsKey(word);
    }

    public String translate(String word){
        return translations.get(word);
    }

    public Map<String, String> getTranslations(){
        return translations;
    }

}
