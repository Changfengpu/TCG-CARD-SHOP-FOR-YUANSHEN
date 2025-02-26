package com.singhand.tacticstemplate.tactistemplate.games;

import java.text.DecimalFormat;
import java.util.*;

class Card {
    String type;
    String rarity;
    String variant;
    String id;
    double value;

    public Card(String type, String rarity, String variant, String id, double value) {
        this.type = type;
        this.rarity = rarity;
        this.variant = variant;
        this.id = id;
        this.value = value;
    }

    @Override
    public String toString() {
        return variant + " " + rarity + " " + id + " Card (Value: " + value + ")";
    }
}

class CardPack {

    public static String getRandomElement(String[] array) {
        Random rand = new Random();
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }
        return array[rand.nextInt(array.length)];
    }

    String packType;
    double price;

    public CardPack(String packType, double price) {
        this.packType = packType;
        this.price = price;
    }

    public List<Card> openPack() {
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            cards.add(generateCard(packType));
        }
        return cards;
    }

    private Card generateCard(String packType) {
        Random rand = new Random();
        double rarityRoll = rand.nextDouble() * 100;
        String rarity;
        double baseValue;

        if (rarityRoll <= 0.25) {
            rarity = "全图";
        } else if (rarityRoll <= 1.25) {
            rarity = "EX";
        } else if (rarityRoll <= 5.25) {
            rarity = "金";
        } else if (rarityRoll <= 13.25) {
            rarity = "银";
        } else {
            rarity = "普通";
        }

        String variant = "";
        double multiplier = 1;

        if (packType.equals("命运") && rand.nextDouble() * 100 <= 0.2) {
            variant = "黑白";
            multiplier *= 100;
        } else if (rand.nextDouble() * 100 <= 0.1) {
            variant = "黑白";
            multiplier *= 100;
        }

        if (rand.nextDouble() * 100 <= 5) {
            variant = variant.isEmpty() ? "闪" : "闪" + variant;
            multiplier *= 5;
        }

        if (variant.equals("闪黑白")) {
            multiplier = 500;
        }

        String id = null;
        if (rarity.equals("全图")) {
            //从globalCards中随机一张
            id = getRandomElement(TCGCardGame.globalCards);
        } else if (rarity.equals("金")) {
            id = getRandomElement(TCGCardGame.goldCards);
        } else if (rarity.equals("银")) {
            id = getRandomElement(TCGCardGame.silverCards);
        } else if (rarity.equals("普通")) {
            id = getRandomElement(TCGCardGame.commonCards);
        } else if (rarity.equals("EX")) {
            id = getRandomElement(TCGCardGame.exCards);
        }
        double value = TCGCardGame.cardPirseMap.get(id) * multiplier;


        return new Card(rarity, rarity, variant, id, value);
    }
}


public class TCGCardGame {
    private static double money = 100;
    private static List<Card> inventory = new ArrayList<>();

    static Map<String, Double> cardPirseMap = new HashMap<>();

    static int openNum = 0;

    // 普通卡牌 - 低级怪物
    static String[] commonCards = {
            "丘丘人", "史莱姆", "盗宝团", "愚人众", "深渊法师", "骗骗花", "雷萤术士", "债务处理人", "冰萤术士", "火斧丘丘人", "水萨满", "岩盔王", "风拳", "雷锤", "冰胖", "火枪", "水胖", "岩龙蜥", "风龙蜥", "雷龙蜥", "冰龙蜥", "火龙蜥", "水龙蜥", "岩龙蜥王",
            "打手丘丘人", "木盾丘丘人", "岩盾丘丘人", "冲锋丘丘人", "爆弹丘丘人", "射手丘丘人", "冰箭丘丘人", "火箭丘丘人", "雷箭丘丘人", "草丘丘萨满", "岩丘丘萨满", "风丘丘萨满", "水丘丘萨满", "岩史莱姆", "冰史莱姆", "草史莱姆", "雷史莱姆", "风史莱姆", "水史莱姆", "火史莱姆", "雷萤", "水萤", "盗宝团斥候", "盗宝团·粉碎者", "盗宝团掘墓者", "盗宝团·神射手", "盗宝团·杂工", "盗宝团·拳术师", "盗宝团·冰之药剂师", "盗宝团·火之药剂师", "盗宝团·雷之药剂师", "盗宝团·水之药剂师", "盗宝团·海上男儿"
    };

    // 银卡 - 精英怪
    static String[] silverCards = {
            "火法师", "冰法师", "水法师", "雷法师", "风法师", "岩法师", "愚人众执行官", "深渊使徒", "深渊咏者", "骗骗花王",
            "狂风之核", "丘丘暴徒", "火斧丘丘暴徒", "木盾丘丘暴徒", "岩盾丘丘暴徒", "冰盾丘丘暴徒", "雷斧丘丘暴徒", "丘丘王", "丘丘岩盔王", "丘丘霜铠王", "丘丘雷兜王", "深渊使者", "深渊使徒·激流", "深渊咏者·紫电", "深渊咏者·渊火", "深渊使徒·霜落", "兽境群狼", "嗜雷·兽境幼兽", "嗜岩·兽境幼兽", "嗜雷·兽境猎犬", "嗜岩·兽境猎犬", "黑蛇众", "黯色空壳·破阵", "黯色空壳·旗令", "黯色空壳·近卫", "黑蛇骑士·斩风之剑", "黑蛇骑士·摧岩之钺", "愚人众精英", "愚人众·火之债务处理人", "愚人众·雷萤术士", "愚人众·冰萤术士", "愚人众·藏镜仕女", "遗迹机关", "遗迹守卫", "遗迹猎者", "遗迹重机", "遗迹机兵", "遗迹巡弋者", "遗迹歼击者", "遗迹防卫者", "遗迹侦察者", "遗迹龙兽", "元能构装体", "元能构装体·力场发生器", "元能构装体·勘探机", "元能构装体·重塑仪", "异种魔兽", "深海龙蜥", "深海龙蜥·原种", "深海龙蜥·吞雷", "深海龙蜥·啮冰", "幼岩龙蜥", "圣骸兽", "圣骸毒蝎", "圣骸赤鹫", "圣骸飞蛇", "敌对生物", "大雪猪王"
    };

    // 金卡 - BOSS
    static String[] goldCards = {
            "无相之风", "无相之雷", "无相之岩", "无相之火", "无相之冰", "无相之水", "急冻树", "爆炎树", "纯水精灵", "古岩龙蜥", "风魔龙", "北风狼", "公子", "女士", "若陀龙王", "雷音权现", "黄金王兽", "遗迹巨蛇", "兆载永劫龙兽", "无相之草"
            , "深罪浸礼者", "沸沸扬扬的草龙"
    };

    // EX卡 - 角色
    static String[] exCards = {
            "神里绫华", "刻晴", "迪卢克", "温迪", "钟离", "胡桃", "甘雨", "魈", "优菈", "阿贝多", "莫娜", "七七", "达达利亚", "可莉", "琴", "丽莎", "凯亚", "安柏", "芭芭拉", "辛焱", "砂糖", "菲谢尔", "诺艾尔",
            "烟绯", "荒泷一斗", "迪奥娜", "五郎", "珊瑚宫心海", "八重神子", "散兵", "行秋", "重云", "凝光", "香菱", "北斗", "申鹤", "云堇", "夜兰", "瑶瑶", "埃洛伊", "赛诺", "提纳里", "柯莱", "多莉", "坎蒂丝", "莱依拉", "绮良良", "米卡", "卡维", "白术", "夏洛蒂", "琳尼特", "林尼", "娜维娅", "菲米尼", "希格雯", "千织", "夏沃蕾", "玛拉妮", "基尼奇", "赛索斯", "欧洛伦", "希诺宁"
    };
    // 全图卡 - 神
    static String[] globalCards = {"雷电将军", "风神温迪", "岩神钟离", "草神纳西妲", "水神芙宁娜", "火神玛薇卡", "冰神巴纳巴斯"};


    static {
        DecimalFormat df = new DecimalFormat("#.00");
        Random rand = new Random();


        // 初始化普通卡牌
        for (String card : commonCards) {
            if (cardPirseMap.containsKey(card)) {
                System.out.println("卡牌" + card + "定义重复，已经忽略");
                continue;
            }
            cardPirseMap.put(card, Double.parseDouble(df.format(rand.nextDouble() * 0.9 + 0.1)));
        }

        // 初始化银卡
        for (String card : silverCards) {
            if (cardPirseMap.containsKey(card)) {
                System.out.println("卡牌" + card + "定义重复，已经忽略");
                continue;
            }
            cardPirseMap.put(card, Double.parseDouble(df.format(rand.nextDouble() * 9 + 1)));
        }

        // 初始化金卡
        for (String card : goldCards) {
            if (cardPirseMap.containsKey(card)) {
                System.out.println("卡牌" + card + "定义重复，已经忽略");
                continue;
            }
            cardPirseMap.put(card, Double.parseDouble(df.format(rand.nextDouble() * 90 + 10)));
        }

        // 初始化EX卡
        for (String card : exCards) {
            if (cardPirseMap.containsKey(card)) {
                System.out.println("卡牌" + card + "定义重复，已经忽略");
                continue;
            }
            cardPirseMap.put(card, Double.parseDouble(df.format(rand.nextDouble() * 900 + 100)));
        }

        // 初始化全图卡
        for (String card : globalCards) {
            if (cardPirseMap.containsKey(card)) {
                System.out.println("卡牌" + card + "定义重复，已经忽略");
                continue;
            }
            cardPirseMap.put(card, Double.parseDouble(df.format(rand.nextDouble() * 9000 + 1000)));
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int baseCardPrise=10;
        int minYunCardPrise=20;

        while (money >= 0) {
            System.out.println("当前余额: " + money);
            System.out.println("选择卡包: 1. 基础卡包 ("+baseCardPrise+"元) 2. 命运卡包 ("+minYunCardPrise+"元) 3.基础卡包批量 4.命运卡包批量 5. 卖卡 6. 退出 ");
            int choice = scanner.nextInt();

            if (choice == 1) {
                buyPack("基础", baseCardPrise);
            } else if (choice == 2) {
                buyPack("命运", minYunCardPrise);
            } else if (choice == 3) {
                System.out.println("请输入开包数量");
                int num = scanner.nextInt();
                buyPackBatch("基础", baseCardPrise, num);
            } else if (choice == 4) {
                System.out.println("请输入开包数量");
                int num = scanner.nextInt();
                buyPackBatch("命运", minYunCardPrise, num);
            } else if (choice == 5) {
                sellCards();
            } else if (choice == 6) {
                break;
            } else {
                System.out.println("无效选择");
            }
        }

        System.out.println("游戏结束！");
    }

    private static void buyPack(String packType, double price) {
        if (money < price) {
            System.out.println("余额不足！");
            return;
        }


        money -= price;
        CardPack pack = new CardPack(packType, price);
        List<Card> cards = pack.openPack();

        System.out.println("你开出了以下卡牌：");
        for (Card card : cards) {
            System.out.println(card);
            openNum++;
            if (card.rarity.equals("全图") && card.variant.equals("闪黑白")) {
                System.out.println("恭喜你以第" + openNum + "张卡开出全图黑白闪卡,游戏结束");
                System.exit(0);
            }
            inventory.add(card);
        }
    }

    private static void buyPackBatch(String packType, double price, int num) {
        for (int i = 0; i < num; i++) {
            if (money <= price) {
                break;
            }
            buyPack(packType, price);
        }
    }

    private static void sellCards() {
        if (inventory.isEmpty()) {
            System.out.println("没有卡牌可卖！");
            return;
        }

        inventory.sort(Comparator.comparingDouble(e -> e.value));

        //倒序
        inventory =reverseList(inventory);

        System.out.println("你的卡牌：");
        for (int i = 0; i < inventory.size(); i++) {
            System.out.println((i + 1) + ". " + inventory.get(i));
            if (i > 10) {
                break;
            }
        }

        System.out.println("选择要卖的卡牌编号 (输入0返回，输入-1卖掉全部)：");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        if (choice > 0 && choice <= inventory.size()) {
            Card card = inventory.remove(choice - 1);
            money += card.value;
            System.out.println("你卖出了 " + card + "，获得 " + card.value + " 元");
        } else if (choice == -1) {
            money += inventory.stream().mapToDouble(e -> e.value).sum();
            inventory.clear();
        }
    }

    // 手动反转 List 的方法
    public static List<Card> reverseList(List<Card> list) {
        int left = 0;
        int right = list.size() - 1;
        while (left < right) {
            // 交换元素
            Card temp = list.get(left);
            list.set(left, list.get(right));
            list.set(right, temp);
            // 移动指针
            left++;
            right--;
        }
        return list;
    }
}