package com.test.plugins.code;

import com.google.common.base.CaseFormat;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 代码生成器，根据数据表名称生成对应的Model、Mapper、Service、Controller简化开发。
 */
public class CodeGenerator {

    private static final String BASE_PACKAGE = "com.test";//项目基础包名称，根据自己的项目修改

    /*生成文件地址配置*/
    private static final String MODEL_PACKAGE = BASE_PACKAGE + ".model";//生成的Model类所在包
    private static final String MAPPER_PACKAGE = BASE_PACKAGE + ".mapper";//生成的Mapper所在包
    private static final String REPOSITORY_PACKAGE = BASE_PACKAGE + ".repository";//生成Repository所在包
    private static final String SERVICE_PACKAGE = BASE_PACKAGE + ".service";//生成的Service所在包
    private static final String SERVICE_IMPL_PACKAGE = BASE_PACKAGE + ".serviceImpl";//生成的ServiceImpl所在包
    private static final String CONTROLLER_PACKAGE = BASE_PACKAGE + ".controller";//生成的Controller所在包

    private static final String MAPPER_INTERFACE_REFERENCE = BASE_PACKAGE + ".common.mapper.MyMapper";//Mapper插件基础接口的完全限定名(第二步提到的核心继承接口Mapper)

    /*数据库配置*/
    private static final String JDBC_URL = "jdbc:mysql://47.106.222.13:3306/remix?characterEncoding=utf8&useSSL=false";//数据库url
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "!Remix2018";
    private static final String JDBC_DIVER_CLASS_NAME = "com.mysql.jdbc.Driver";

    private static final String PROJECT_PATH = System.getProperty("user.dir");//项目在硬盘上的基础路径
    static final String TEMPLATE_FILE_PATH = PROJECT_PATH + "/src/main/resources/templates/code";//模板位置

    private static final String JAVA_PATH = "/src/main/java"; //java文件路径
    private static final String RESOURCES_PATH = "/src/main/resources";//资源文件路径

    private static final String PACKAGE_PATH_REPOSITORY = packageConvertPath(REPOSITORY_PACKAGE);
    private static final String PACKAGE_PATH_SERVICE = packageConvertPath(SERVICE_PACKAGE);//生成的Service存放路径
    private static final String PACKAGE_PATH_SERVICE_IMPL = packageConvertPath(SERVICE_IMPL_PACKAGE);//生成的Service实现存放路径
    private static final String PACKAGE_PATH_CONTROLLER = packageConvertPath(CONTROLLER_PACKAGE);//生成的Controller存放路径

    static final String AUTHOR = "SimonSun";//@author
    static final String CREATE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());//@date

    /*main函数入口,放入表名运行即可生成代码*/
    public static void main(String[] args) {
        //genCodeByCustomModelName("输入表名","输入自定义Model名称");
        //genCodeByCustomModelName("t_sell_song", "SellSong");
        //genCodeByCustomModelName("t_sell_song_sort", "SellSongSort");
//        genCodeByCustomModelName("t_friend_circle_message", "FriendCircleMessage");
//        genCodeByCustomModelName("t_friend_circle_timeline", "FriendCircleTimeline");
//        genCode("app_search_last_info", "app_terminal_management");
        genCodeByCustomModelName("t_member", "Member");
//        genCodeByCustomModelName("t_member_ext", "MemberExt");
//        genCodeByCustomModelName("t_member_set", "MemberSet");
//        genCodeByCustomModelName("t_account_bind","AccountBind");
//        genCodeByCustomModelName("t_message_comment","MessageComment");
//        genCodeByCustomModelName("t_message_comment_reply", "MessageCommentReply");
//        genCodeByCustomModelName("t_message_like", "MessageLike");//朋友圈消息点赞
//        genCodeByCustomModelName("t_message_nowatch_user", "MessageNoWatchUser");
//        genCodeByCustomModelName("t_message_remind_user", "MessageRemindUser");
//        genCodeByCustomModelName("t_message_watch_user", "MessageWatchUser");
//        genCodeByCustomModelName("t_my_fans", "MyFans");
//        genCodeByCustomModelName("t_my_friend", "MyFriend");
//        genCodeByCustomModelName("t_my_friend_apply", "MyFriendApply");
   //     genCodeByCustomModelName("t_my_playlist", "MyPlayList");
    //    genCodeByCustomModelName("t_my_playlist_song", "MyPlayListSong");
//    	genCodeByCustomModelName("t_new_singer", "NewSinger"); 			 //最新歌手
//    	genCodeByCustomModelName("t_new_song", "NewSong"); 				 //最新歌曲
//    	genCodeByCustomModelName("t_top_song", "TopSong");				 //榜单
//    	genCodeByCustomModelName("t_pop_singer", "PopSinger");			 //热门歌手
//    	genCodeByCustomModelName("t_pop_song", "PopSong");			     //热门歌曲
//      genCodeByCustomModelName("t_song_comment", "SongComment");
//      genCodeByCustomModelName("t_song_comment_reply", "SongCommentReply");
//      genCodeByCustomModelName("t_song_like", "SongLike");
//      genCodeByCustomModelName("t_share_song", "ShareSong");
//    	genCodeByCustomModelName("t_today_recommend", "TodayRecommend"); //今日推荐
//    	genCodeByCustomModelName("t_trade_song", "TradeSong");//交易歌曲
//      genCodeByCustomModelName("t_song_ablum", "SongAlbum");//专辑
//      genCodeByCustomModelName("t_song", "Song");//歌曲
	    //genCodeByCustomModelName("t_bill", "Bill");//账单
        //genCodeByCustomModelName("t_bill_detail", "BillDetail");
//    	genCodeByCustomModelName("t_user_wallet", "UserWallet");//钱包
//    	genCodeByCustomModelName("t_bought_song", "BoughtSong");//我购买的歌曲
//    	genCodeByCustomModelName("t_contact_us", "ContactUs");//联系我们
//    	genCodeByCustomModelName("t_complaint_suggestion", "ComplaintSuggestion");//投诉与建议
//    	genCodeByCustomModelName("app_search_hot_key", "SearchHotKey");//热门搜索
//    	genCodeByCustomModelName("app_search_last_info", "SearchLastInfo");//搜索记录
//    	genCodeByCustomModelName("t_pop_song_sheet_palylist", "PopSongSheetPalylist");//热门歌单单曲列表
//    	genCodeByCustomModelName("t_pop_song_sheet", "PopSongSheet");//热门歌单
    	//genCodeByCustomModelName("t_news", "News");//热门歌单
    	//genCodeByCustomModelName("t_job", "Job");//热门歌单
        //genCodeByCustomModelName("t_song_file", "SongFile");//歌曲文件
        //genCodeByCustomModelName("t_s_log", "SLog");//系统日志
        //genCodeByCustomModelName("t_s_type", "SType");
        //genCodeByCustomModelName("t_s_typegroup", "STypeGroup");
    	 //genCodeByCustomModelName("t_founder_info", "FounderInfo");//创始人
    	
    	
    }

    /**
     * 通过数据表名称生成代码，Model 名称通过解析数据表名称获得，下划线转大驼峰的形式。
     * 如输入表名称 "t_user_detail" 将生成 TUserDetail、TUserDetailMapper、TUserDetailService ...
     * @param tableNames 数据表名称...
     */
    public static void genCode(String... tableNames) {
        for (String tableName : tableNames) {
            genCodeByCustomModelName(tableName, null);
        }
    }

    /**
     * 通过数据表名称，和自定义的 Model 名称生成代码
     * 如输入表名称 "t_user_detail" 和自定义的 Model 名称 "User" 将生成 User、UserMapper、UserService ...
     * @param tableName 数据表名称
     * @param modelName 自定义的 Model 名称
     */
    public static void genCodeByCustomModelName(String tableName, String modelName) {
        genModelAndMapper(tableName, modelName);
       //  genRepository(tableName, modelName);
		// genService(tableName, modelName);
		//genController(tableName, modelName);

    }


    public static void genModelAndMapper(String tableName, String modelName) {
        Context context = new Context(ModelType.FLAT);
        context.setId("Potato");
        context.setTargetRuntime("MyBatis3Simple");
        context.addProperty(PropertyRegistry.CONTEXT_BEGINNING_DELIMITER, "`");
        context.addProperty(PropertyRegistry.CONTEXT_ENDING_DELIMITER, "`");

        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setConnectionURL(JDBC_URL);
        jdbcConnectionConfiguration.setUserId(JDBC_USERNAME);
        jdbcConnectionConfiguration.setPassword(JDBC_PASSWORD);
        jdbcConnectionConfiguration.setDriverClass(JDBC_DIVER_CLASS_NAME);
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);

        PluginConfiguration pluginConfiguration = new PluginConfiguration();
        pluginConfiguration.setConfigurationType("tk.mybatis.mapper.generator.MapperPlugin");
        pluginConfiguration.addProperty("mappers", MAPPER_INTERFACE_REFERENCE);
        context.addPluginConfiguration(pluginConfiguration);

        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetProject(PROJECT_PATH + JAVA_PATH);
        javaModelGeneratorConfiguration.setTargetPackage(MODEL_PACKAGE);
        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);

        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        sqlMapGeneratorConfiguration.setTargetProject(PROJECT_PATH + RESOURCES_PATH);
        sqlMapGeneratorConfiguration.setTargetPackage("mapping");
        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);

        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
        javaClientGeneratorConfiguration.setTargetProject(PROJECT_PATH + JAVA_PATH);
        javaClientGeneratorConfiguration.setTargetPackage(MAPPER_PACKAGE);
        javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
        context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);

        TableConfiguration tableConfiguration = new TableConfiguration(context);
        tableConfiguration.setTableName(tableName);
        if (StringUtils.isNotEmpty(modelName)) {tableConfiguration.setDomainObjectName(modelName);}
        tableConfiguration.setGeneratedKey(new GeneratedKey("id", "Mysql", true, null));
        context.addTableConfiguration(tableConfiguration);

        List<String> warnings;
        MyBatisGenerator generator;
        try {
            Configuration config = new Configuration();
            config.addContext(context);
            config.validate();

            boolean overwrite = true;
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            warnings = new ArrayList<String>();
            generator = new MyBatisGenerator(config, callback, warnings);
            generator.generate(null);
        } catch (Exception e) {
            throw new RuntimeException("生成Model和Mapper失败", e);
        }

        if (generator.getGeneratedJavaFiles().isEmpty() || generator.getGeneratedXmlFiles().isEmpty()) {
            throw new RuntimeException("生成Model和Mapper失败：" + warnings);
        }
        if (StringUtils.isEmpty(modelName)) {modelName = tableNameConvertUpperCamel(tableName);}
        System.out.println(modelName + ".java 生成成功");
        System.out.println(modelName + "Mapper.java 生成成功");
        System.out.println(modelName + "Mapper.xml 生成成功");

        //覆盖默认生成不符合需求的Model
        FreeMarkerGeneratorUtil.generatorMvcCode(
                JDBC_DIVER_CLASS_NAME,
                JDBC_URL,
                JDBC_USERNAME,
                JDBC_PASSWORD,
                tableName,
                modelName,
                MODEL_PACKAGE);
    }

    public static void genRepository(String tableName, String modelName) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();

            Map<String, Object> data = new HashMap<>();
            data.put("AUTHOR", AUTHOR);
            data.put("CREATE", CREATE);
            String modelNameUpperCamel = StringUtils.isEmpty(modelName) ? tableNameConvertUpperCamel(tableName) : modelName;
            data.put("modelNameUpperCamel", modelNameUpperCamel);
            data.put("modelNameLowerCamel", modelNameConvertLowerCamel(modelNameUpperCamel));
            data.put("basePackage", BASE_PACKAGE);

            File file = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_REPOSITORY + modelNameUpperCamel + "Repository.java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("repository.ftl").process(data,
                    new FileWriter(file));
            System.out.println(modelNameUpperCamel + "Repository.java 生成成功");

        } catch (Exception e) {
            throw new RuntimeException("生成repository失败", e);
        }
    }

    public static void genService(String tableName, String modelName) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();

            Map<String, Object> data = new HashMap<>();
            data.put("AUTHOR", AUTHOR);
            data.put("CREATE", CREATE);
            String modelNameUpperCamel = StringUtils.isEmpty(modelName) ? tableNameConvertUpperCamel(tableName) : modelName;
            data.put("modelNameUpperCamel", modelNameUpperCamel);
            data.put("modelNameLowerCamel", modelNameConvertLowerCamel(modelNameUpperCamel));
            data.put("basePackage", BASE_PACKAGE);

            File file = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_SERVICE + modelNameUpperCamel + "Service.java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("service.ftl").process(data,
                    new FileWriter(file));
            System.out.println(modelNameUpperCamel + "Service.java 生成成功");

            File file1 = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_SERVICE_IMPL + modelNameUpperCamel + "ServiceImpl.java");
            if (!file1.getParentFile().exists()) {
                file1.getParentFile().mkdirs();
            }
            cfg.getTemplate("service-impl.ftl").process(data,
                    new FileWriter(file1));
            System.out.println(modelNameUpperCamel + "ServiceImpl.java 生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成Service失败", e);
        }
    }

    public static void genController(String tableName, String modelName) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();

            Map<String, Object> data = new HashMap<>();
            data.put("AUTHOR", AUTHOR);
            data.put("CREATE", CREATE);
            String modelNameUpperCamel = StringUtils.isEmpty(modelName) ? tableNameConvertUpperCamel(tableName) : modelName;
            data.put("baseRequestMapping", modelNameConvertMappingPath(modelNameUpperCamel));
            data.put("modelNameUpperCamel", modelNameUpperCamel);
            data.put("modelNameLowerCamel", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelNameUpperCamel));
            data.put("basePackage", BASE_PACKAGE);

            File file = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_CONTROLLER + modelNameUpperCamel + "Controller.java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            //cfg.getTemplate("controller-restful.ftl").process(data, new FileWriter(file));
            cfg.getTemplate("controller.ftl").process(data, new FileWriter(file));

            System.out.println(modelNameUpperCamel + "Controller.java 生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成Controller失败", e);
        }

    }

    private static freemarker.template.Configuration getConfiguration() throws IOException {
        freemarker.template.Configuration cfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_23);
        cfg.setDirectoryForTemplateLoading(new File(TEMPLATE_FILE_PATH));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        return cfg;
    }

    private static String tableNameConvertLowerCamel(String tableName) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName.toLowerCase());
    }

    private static String tableNameConvertUpperCamel(String tableName) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName.toLowerCase());

    }

    private static String modelNameConvertLowerCamel(String modelName){
        if(Character.isLowerCase(modelName.charAt(0)))
            return modelName;
        else
            return (new StringBuilder()).append(Character.toLowerCase(modelName.charAt(0))).append(modelName.substring(1)).toString();
    }

    private static String tableNameConvertMappingPath(String tableName) {
        tableName = tableName.toLowerCase();//兼容使用大写的表名
        return "/" + (tableName.contains("_") ? tableName.replaceAll("_", "/") : tableName);
    }

    private static String modelNameConvertMappingPath(String modelName) {
        String tableName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, modelName);
        return tableNameConvertMappingPath(tableName);
    }

    private static String packageConvertPath(String packageName) {
        return String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
    }

}