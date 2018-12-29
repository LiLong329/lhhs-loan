/**
 * Created by chen on 2015/12/15.
 */
var Area={
//    '北京':['东城', '西城', '朝阳', '丰台', '石景山', '海淀', '顺义', '通州', '大兴', '房山', '门头沟', '昌平', '平谷', '密云', '怀柔', '延庆'],
//    '上海':['杨浦', '虹口', '闸北', '黄浦', '静安', '卢湾', '普陀', '长宁', '徐汇', '浦东新', '闵行', '奉贤', '松江', '金山', '宝山', '嘉定', '青浦'],
//    '天津':['和平', '河北', '河东', '河西', '南开', '红桥', '东丽', '西青', '津南', '北辰', '武清', '宝坻', '静海', '宁河', '滨海新'],
//    '重庆':['渝中', '大渡口', '江北', '沙坪坝', '九龙坡', '南岸', '北碚', '綦江', '双桥', '渝北', '巴南', '万州', '涪陵', '黔江', '长寿', '江津', '合川', '永川', '南川'],
	'北京':["北京市"],
	'上海':["上海市"],
	'天津':["天津市"],
	'重庆':["重庆市"],
	'河北':["石家庄市", "张家口市", "承德市", "秦皇岛市", "唐山市", "廊坊市", "保定市", "沧州市", "衡水市", "邢台市", "邯郸市"],
	'山西':["太原市", "临汾市", "运城市", "大同市", "朔州市", "阳泉市", "长治市", "晋城市", "忻州市", "吕梁市", "晋中市"],
	'辽宁':["沈阳市", "朝阳市", "阜新市", "铁岭市", "抚顺市", "本溪市", "辽阳市", "鞍山市", "丹东市", "大连市", "营口市", "盘锦市", "锦州市", "葫芦岛市"],
	'吉林':["长春市", "白城市", "松原市", "吉林市", "四平市", "辽源市", "通化市", "白山市", "延边市"],
	'黑龙江':["哈尔滨市", "齐齐哈尔市", "黑河市", "大庆市", "伊春市", "鹤岗市", "佳木斯市", "双鸭山市", "七台河市", "鸡西市", "牡丹江市", "绥化市", "大兴安岭市"],
	'江苏':["南京市", "徐州市", "连云港市", "宿迁市", "淮安市", "盐城市", "扬州市", "泰州市", "南通市", "镇江市", "常州市", "无锡市", "苏州市"],
	'浙江':["杭州市", "湖州市", "嘉兴市", "舟山市", "宁波市", "绍兴市", "金华市", "台州市", "温州市", "丽水市", "衢州市"],
	'安徽':["合肥市", "宿州市", "淮北市", "阜阳市", "蚌埠市", "淮南市", "滁州市", "马鞍山市", "芜湖市", "铜陵市", "安庆市", "黄山市", "六安市", "巢湖市", "池州市", "宣城市", "亳州市"],
	'福建':["福州市", "南平市", "三明市", "莆田市", "泉州市", "厦门市", "漳州市", "龙岩市", "宁德市"],
	'江西':["南昌市", "九江市", "景德镇市", "鹰潭市", "新余市", "萍乡市", "赣州市", "上饶市", "抚州市", "宜春市", "吉安市"],
	'山东':["济南市", "聊城市", "德州市", "东营市", "淄博市", "潍坊市", "烟台市", "威海市", "青岛市", "日照市", "临沂市", "枣庄市", "济宁市", "泰安市", "莱芜市", "滨州市", "菏泽市"],
	'河南':["郑州市", "三门峡市", "洛阳市", "焦作市", "新乡市", "鹤壁市", "济源市", "安阳市", "濮阳市", "开封市", "商丘市", "许昌市", "漯河市", "平顶山市", "南阳市", "信阳市", "周口市", "驻马店市"],
	'湖北':["武汉市", "十堰市", "荆门市", "孝感市", "黄冈市", "鄂州市", "黄石市", "咸宁市", "荆州市", "宜昌市", "襄阳市", "随州市", "恩施市", "仙桃市", "潜江市", "天门市", "神农架市"],
	'湖南':["长沙市", "张家界市", "常德市", "益阳市", "岳阳市", "株洲市", "湘潭市", "衡阳市", "郴州市", "永州市", "邵阳市", "怀化市", "娄底市", "湘西市"],
	'广东':["广州市", "清远市", "中山市", "韶关市", "河源市", "梅州市", "潮州市", "汕头市", "揭阳市", "汕尾市", "惠州市", "东莞市", "深圳市", "珠海市", "江门市", "佛山市", "肇庆市", "云浮市", "阳江市", "茂名市", "湛江市"],
	'海南':["海口市", "三亚市", "三沙市", "儋州市"],
	'四川':["成都市", "广元市", "绵阳市", "德阳市", "南充市", "广安市", "遂宁市", "内江市", "乐山市", "自贡市", "泸州市", "宜宾市", "攀枝花市", "巴中市", "达州市", "资阳市", "眉山市", "雅安市", "阿坝市", "甘孜市", "凉山市"],
	'贵州':["贵阳市", "六盘水市", "遵义市", "毕节市", "铜仁市", "安顺市", "黔东南市", "黔南市", "黔西南市"],
	'云南':["昆明市", "曲靖市", "玉溪市", "丽江市", "昭通市", "普洱市", "临沧市", "保山市", "德宏市", "怒江市", "迪庆市", "大理市", "楚雄市", "红河市", "文山市", "西双版纳市"],
	'陕西':["西安市", "延安市", "铜川市", "渭南市", "咸阳市", "宝鸡市", "汉中市", "榆林市", "商洛市", "安康市"],
	'甘肃':["兰州市", "嘉峪关市", "金昌市", "白银市", "天水市", "酒泉市", "张掖市", "武威市", "庆阳市", "平凉市", "定西市", "陇南市", "临夏市", "甘南市"],
	'青海':["西宁市", "海东市", "海北市", "黄南市", "海南市", "果洛市", "玉树市", "海西市"],
	'内蒙古':["呼和浩特市", "包头市", "乌海市", "赤峰市", "通辽市", "呼伦贝尔市", "兴安市", "锡林郭勒市", "乌兰察布市", "鄂尔多斯市", "巴彦淖尔市", "阿拉善市"],
	'广西':["南宁市", "桂林市", "柳州市", "梧州市", "贵港市", "玉林市", "钦州市", "北海市", "防城港市", "来宾市", "百色市", "河池市", "崇左市", "贺州市"],
	'西藏':["拉萨市", "那曲市", "昌都市", "林芝市", "山南市", "日喀则市", "阿里市"],
	'宁夏':["银川市", "石嘴山市", "吴忠市", "固原市", "中卫市"],
	'新疆':["乌鲁木齐市", "克拉玛依市", "吐鲁番市", "喀什市", "阿克苏市", "和田市", "哈密市", "克孜勒苏市", "博尔塔拉市", "昌吉市", "巴音郭楞市", "伊犁市", "塔城市", "阿勒泰市"]
};
function get_province_options(){
	var options = "";
	for(var singe in Area){
		options += "<option>" + singe + "</option>";
	}
	return options;
};
function get_city_options(province){
	var options = "";
    if(!province) return;
    if(province.indexOf('请选择')>=0) return;
    if(typeof(Area[province])=='undefined') return;
    for(var city in Area[province]) {
    	options += "<option>" + Area[province][city] + "</option>";
    }
    return options;
};
function get_province_array(){
	var provinceList=new Array();
	for(var singe in Area){
		provinceList.push(singe);
	}
	return provinceList;
};
function get_city_array(province){
    if(!province) return;
    if(province.indexOf('请选择')>=0) return;
    if(typeof(Area[province])=='undefined') return;
    return Area[province];
};