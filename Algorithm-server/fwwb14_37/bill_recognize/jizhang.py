# -*- coding:utf-8 -*-
import copy


class JiZhang:
    superclass = None
    subclass = None
    money = None
    shop = None
    detailed = None


kind = ['traffic', 'office', 'daily', 'service', 'digital_appliance',
        'rent_decoration', 'communication', 'lodging', 'post',
        'medical_treatment', 'repast', 'foodstuff', 'raiment',
        'vehicle', 'education', 'other']
kind_classify = [('出行', None), ('其他', '办公'), ('购物', '日用'),
                 (None, None), ('购物', '数码电器'), ('生活', '房租装饰'),
                 ('生活', '生活缴费'), ('生活', '住宿'), ('生活', '快递'),
                 ('生活', '医疗'), ('饮食', '餐饮'), ('饮食', None),
                 ('购物', '服饰'), ('出行', '用车'), ('教育', '学习'),
                 (None, None), ]

type = ['air_transport', 'blockchain_electronic_invoice', 'education_receipt',
        'general_machine_invoice', 'highway_passenger_invoice', 'machine_printed_invoice',
        'medical_receipt', 'motor_vehicle_sale_invoice', 'non_tax_income_unified_bill',
        'parking_invoice', 'passenger_transport_invoice', 'quota_invoice',
        'shipping_invoice', 'shop_receipt', 'taxi_ticket', 'train_ticket',
        'travel_transport', 'used_car_purchase_invoice', 'vat_common_invoice',
        'vat_electronic_invoice', 'vat_electronic_special_invoice',
        'vat_electronic_toll_invoice', 'vat_electronic_invoice_new',
        'vat_electronic_special_invoice_new', 'vat_invoice_sales_list',
        'vat_roll_invoice', 'vat_special_invoice', 'vat_transport_invoice',
        'vehicle_toll', 'other']

vat_items = ('vat_invoice_price', 'vat_invoice_seller_name', 'vat_invoice_goods_list')
moto_items = ('vehicle_invoice_total_price_digits', 'vehicle_invoice_dealer', None)
usedcar_items = ('vehicle_invoice_total_price_digits', 'vehicle_invoice_seller', 'vehicle_invoice_note')
vatroll_items = ('total_money', 'sold_name', None)
toll_items = ('money', None, None)
quota_items = ('money_small', None, None)
taxi_items = ('sum', None, None)
air_items = ('total', 'issued_by', None)
train_items = ('price', None, None)
general_items = ('money', 'seller', None)
ship_items = ('money', None, None)
passenger_items = ('money', None, None)
parking_items = ('money', None, None)
vatsales_items = ('tax_total', 'seller_name', None)
shop_items = ('money', 'shop', 'sku')
medical_items = ('amount_small', 'medical_institution_type', None)
travel_items = ('total_money', None, None)
income_items = ('TotalAmount', 'ItemUnit', 'Remark')
none_items = (None, None, None)  # education_receipt、vat_transport_invoice 无法结构化识别

type_classify = [('出行', '飞机', air_items), (None, None, vat_items), ('教育', '学习', none_items),
                 (None, None, general_items), ('出行', '客车', passenger_items), (None, None, vat_items),
                 ('生活', '医疗', medical_items), ('出行', '用车', moto_items), ('收入', '其他收入', income_items),
                 ('出行', '停车费', parking_items), ('出行', None, passenger_items), (None, None, quota_items),
                 ('出行', '船运', ship_items), (None, None, shop_items), ('出行', '打车', taxi_items),
                 ('出行', '火车', train_items), ('出行', None, travel_items), ('出行', '用车', usedcar_items),
                 ('税务', '增值税', vat_items), ('税务', '增值税', vat_items), ('税务', '增值税', vat_items),
                 ('税务', '增值税', vat_items), ('税务', '增值税', vat_items), ('税务', '增值税', vat_items),
                 ('税务', '增值税', vatsales_items), ('税务', '增值税', vatroll_items), ('税务', '增值税', vat_items),
                 ('税务', '增值税', none_items), ('出行', '过路费', toll_items), (None, None, none_items), ]

jz = JiZhang()


def first_classify(bill):
    i = kind.index(bill['kind'])
    jz.superclass = kind_classify[i][0]
    jz.subclass = kind_classify[i][1]


def second_classify(bill):
    i = type.index(bill['type'])
    if jz.superclass == None:
        jz.superclass = type_classify[i][0]
    if jz.subclass == None:
        jz.superclass = type_classify[i][1]
    return i


def other_items(bill, i):
    try:
        item_list = bill['item_list']
        item_names = type_classify[i][2]
        for item in item_list:
            if item['key'] == item_names[0]:
                jz.money = item['value']
            elif item['key'] == item_names[1]:
                jz.shop = item['value']
            elif item['key'] == item_names[2]:
                jz.detailed = item['value'].replace('\n', ' ')
    except KeyError as e:
        pass


def jizhang_use_api(bill):
    first_classify(bill)
    i = second_classify(bill)
    other_items(bill, i)

    return {
        'superclass': jz.superclass,
        'subclass': jz.subclass,
        'money': jz.money,
        'shop': jz.shop,
        'detailed': jz.detailed,
    }


repast_must = ["麦当劳", "肯德基", "汉堡王", "海底捞", "醉仙楼"]
clothing_must = ["优衣库", "海澜之家"]
bath_must = ['温泉', '汗蒸']

daily_maybe = ["购物", "广场", "百货", "王府井", "大润发", "超市", "商场", "便利", "生活"]

repast = ["米线", "鸡腿", "羊", "肥牛", "宽粉", "面条", "饭", "海鲜", "火锅", "鱼粉", "蒸饺", "自助", "味碟", "麻辣烫", "生鲜", "菌汤", "鸭脖", "虾滑",
          "烧", "烤肠", "鸳鸯锅", "小酥肉", "豆花", "猪头", "外卖", "豆皮", "番茄锅", "堡", "油条", "拼盘", "水饺", "沙拉", "挂面", "餐饮", "鸡", "大米",
          "披萨", "汤包", "羊蹄", "餐厅", "筷子", "翅中", "饿了么", "牛", "米饭", "小笼包", "豆腐皮", "虾", "下酒菜", "毛肚", "天妇罗", "茅台", "蒸", "豆腐",
          "猪", "红薯", "章鱼小丸子", "炒", "鸡胸", "堂食", "肉酱面", "铁板烧", "牛排", "炒饭", "土豆片", "赖茅", "牛肉", "糖醋里脊", "清水锅", "烧烤", "红油锅",
          "美蛙", "鸡肉卷", "茶", "美团", "鱼", "伊利", "苕皮", "肉", "香菜", "海带片", "牛柳", "猪脚", "烩面", "小油条"]

snack = ["好丽友", "可比克", "绿箭", "辣条", "笨笨狗", "冷饮", "沙琪玛", "元気森林", "千层脆", "山楂树下", "健力宝", "王老吉", "伊利", "盼盼", "苕皮", "统一",
         "旺仔", "面包", "达利园", "仙贝", "东方树叶", "三得利", "士力架", "点心", "大白兔", "小馒头", "真果粒", "烧仙草", "徐福记", "干脆面", "费列罗", "卫龙",
         "美丹", "百奇", "娃哈哈", "果丹皮", "冰淇淋", "薯片", "锐澳", "小浣熊", "雀巢", "三只松鼠", "奶油", "佳龙", "派", "优酸乳", "安慕希", "香肠", "蜜饯",
         "鱼干", "海河", "谷粒多", "巧克力", "糖", "AD钙", "奶酪", "蛋糕", "红牛", "嘉士利", "牛肉干", "脉动", "上好佳", "阿尔卑斯", "真巧", "健达", "杨枝甘露",
         "德芙", "罐头", "啵啵", "百草味", "甜筒", "铜锣烧", "星球杯", "呀土豆", "肉脯", "康师傅", "古茗", "纯甄", "乐吧", "可乐", "雪饼", "洋葱圈", "营养快线",
         "桃酥", "芬达", "炫迈", "好吃点", "虾条", "猫耳朵", "喜之郎", "趣多多", "百醇", "优乐美", "茶", "海苔", "君乐宝", "奥利奥", "格力高", "麦丽素", "锅巴",
         "加多宝", "星巴克", "浪味仙", "花花牛", "雪碧", "芋泥", "金典", "米老头", "芝士条", "果然多", "脆香米", "蛋卷", "曲奇", "魔芋", "甜甜圈", "蘑古力",
         "李子园", "金丝猴", "好多鱼", "爆米花", "果冻", "乐事", "发酵乳", "银鹭", "Q蒂", "米多奇", "纳宝帝", "零食", "益达", "蒙牛", "葡萄干", "酥", "妙脆角",
         "饼干", "凤爪", "标准糖", "特仑苏", "百力滋", "泡芙", "好有趣", "溜溜梅", "卤鸡蛋", "旺旺", "奶茶", "七分甜", "话梅", "不另外加糖"]

garden = ["木耳", "桂圆", "洋芋", "韭菜", "韭黄", "山楂", "西红柿", "莴苣", "山药", "西兰花", "柿子", "葫芦", "马铃薯", "瓜", "橘", "石榴", "芒果", "魔芋",
          "牛油果", "菇", "竹荪", "牛肝菌", "椰子", "椒", "柠檬", "枇杷", "果业", "香蕉", "芋头", "李子", "水果", "无花果", "花生", "板栗", "青柠", "萝卜",
          "茄子", "山竹", "紫薯", "梨", "银耳", "苹果", "桑葚", "蔬菜", "葡萄", "梅子", "松茸", "草莓", "榴莲", "罗汉果", "海带", "毛红丹", "秋葵", "龙眼",
          "菊苣", "柚子", "橙", "大蒜", "提子", "笋", "姜", "红豆", "番茄", "牛蒡", "百香果", "菜", "鱼腥草", "葱", "玉米", "豆", "茴香", "甘蓝", "樱桃",
          "荔枝", "芜菁", "菠萝", "莲藕", "薄荷", "甘蔗", "桃", "灵芝", "红枣", "火龙果", "红薯", "芽", "圣女果", "杏", "芝麻", "桔", "上海青", "蓝莓",
          "黄瓜", "蒜苗", "大蕉", "水芹"]

daily = ["洗衣粉", "洗衣液", "蚊香", "洗发液", "湿巾", "纸", "沐浴露", "沐浴乳", "垃圾袋", "玻璃杯", "皂", "礼盒", "花露水", "黄金叶", "洗发露", "家汇", "黄山烟",
         "牙膏", "名创优品", "兰香商贸", "阿道夫", "维达", "牙刷", "衣架", "洗洁精", "沐浴原液", "黄鹤楼"]

cosmetic = ["唇膏", "粉底液", "眼影", "dior", "chanel", "散粉", "精华", "香水", "面霜", "圣罗兰", "妆", "妝", "YSL", "迪奥", "香奈儿", "乳液",
            "华露", "定妆", "纪梵希", "隔离霜", "隔離霜"]

luxury = ["路易威登"]

hobby = ["积木", '戏剧']

bus_subway = ["公交", "地铁"]

railway = ["火车", "高铁", "二等座", "一等座", "铁路", "卧铺"]

health = ["心内科", "挂号", "健康", "诊", "妇幼", "保健"]

car = ["石油", "石化", "加油", "二手车", "4S", "停车"]

entertainment = ["电影", "影城", "3D眼镜", "影片", "影厅"]

cloth = ["男童", "女童", "水洗", "西装", "男装", "外套", "T恤", "针织", "马克华菲", "手表", "卫衣", "七匹狼", "毛衣", "夹克", "布", "汉服", "羽绒服", "衣",
         "棉衣", "女装", "衬衫", "卫衣", "袜", "鞋", "XL", "依", "吊牌", "干洗", "裤", "服装", "安全裤", "秋衣", "尺码", "棉裤", "XM", "洗涤"]


def outstanding_posibility(l: list):
    idx = -1
    for i in range(len(l)):
        l_without_i = copy.deepcopy(l)
        l_without_i.pop(i)
        outstanding = True
        for j in l_without_i:
            if (l[i] > 1.5 * j) is False:
                outstanding = False
                break
        if outstanding:
            idx = i
            break
    return idx


def jizhang_without_api(all_words: str):
    musts = [(bath_must, ('休闲', '洗浴按摩')), (clothing_must, ('购物', '服饰')), (repast_must, ('饮食', '餐饮')),
             (luxury, ('购物', '奢侈品'))]
    is_must = False
    for must in musts:
        if True in map(lambda x: x in all_words, must[0]):
            jz.superclass, jz.subclass = must[1]
            is_must = True
    if not is_must:
        many_possibilities = [(repast, ('饮食', '餐饮')),
                              (snack, ('饮食', '零食')), (garden, ('饮食', '果蔬')), (daily, ('购物', '日用')), ]
        possible_list = [0, 0, 0, 0]

        for i in range(len(many_possibilities)):
            possible_list[i] = sum(
                map(lambda x: all_words.count(x), many_possibilities[i][0]))

        if not all(i == 0 for i in possible_list):

            is_daily_maybe = False
            for d in daily_maybe:
                if d in all_words:
                    is_daily_maybe = True
                    break

            if is_daily_maybe:
                outsanding_idx = outstanding_posibility(possible_list)
                if outsanding_idx != -1:
                    jz.superclass, jz.subclass = many_possibilities[outsanding_idx][1]
                else:
                    jz.superclass, jz.subclass = '购物', '日用'

            else:
                jz.superclass, jz.subclass = many_possibilities[possible_list.index(max(possible_list))][1]


        else:
            other_posibilitis = [(cloth, ('购物', '服饰')), (hobby, ('休闲', '爱好')),
                                 (health, ('生活', '医疗')), (railway, ('出行', '火车')),
                                 (bus_subway, ('出行', '公交地铁')), (car, ('出行', '用车')),
                                 (cosmetic, ('购物', '化妆品')), (luxury, ('购物', '奢侈品')),
                                 (entertainment, ('休闲', '娱乐'))]
            for p in other_posibilitis:
                if True in map(lambda x: x in all_words, p[0]):
                    jz.superclass, jz.subclass = p[1]

    if (jz.superclass or jz.subclass) is None:
        is_daily_maybe = False
        for d in daily_maybe:
            if d in all_words:
                is_daily_maybe = True
                break
        if is_daily_maybe:
            jz.superclass, jz.subclass = ('购物', '日用')

    return {
        'superclass': jz.superclass,
        'subclass': jz.subclass,
        'money': jz.money,
        'shop': jz.shop,
        'detailed': jz.detailed,
    }
