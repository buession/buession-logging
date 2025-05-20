window.ydoc_plugin_search_json = {
  "文档": [
    {
      "title": "快速入门",
      "content": "TIP\n\n官方指南假设您已了解\"JAVA\"方面的相关知识。\n\nBuession Framework 它是日常工作中常见的通用技术需求二次封装，提供了众多常用的类库、方法、注解；同时基于 springfrawork、jsckson、jedis、apache httpcomponents、okhttp3 等等众多的优秀的三方工具的标准化的、统一的类库的上层封装，简化框架切换带来的成本。更多介绍开源查阅框架介绍。Buession Framework 还在引用三方类库时，确保了版本的一致性，避免在不用三方类库引用的同一个三方类库版本不一致的情况。您可以根据本文档中的示例，快速熟悉 Buession Framework 的使用方法。",
      "url": "/docs/quickstart.html",
      "children": [
        {
          "title": "下一步可做什么？",
          "url": "/docs/quickstart.html#下一步可做什么？",
          "content": "下一步可做什么？您对 Buession Framework 大致了解后，您接下来可以做以下事情：了解兼容性：了解 Buession Framework 的兼容性\n安装：安装/引用 Buession Framework\n使用：开始使用 Buession Framework 功能\n"
        }
      ]
    },
    {
      "title": "框架介绍",
      "content": "",
      "url": "/docs/intro.html",
      "children": [
        {
          "title": "Buession Framework 框架是什么？",
          "url": "/docs/intro.html#buession-framework-框架是什么？",
          "content": "Buession Framework 框架是什么？Buession Framework 框架不是重复造车轮，它不是其它框架的替代品。它是基于各开源框架的日常工作中常见的通用技术需求二次封装本地化的数据验证，如：QQ、电话号码、身份证号码、邮政编码\n常用 DAO 层操作，如：插入、替换、根据主键获取记录、获取单条记录、获取多条记录\n应用层实现数据库读写分离\nredis 操作兼容原生 API 的前提下，同时实现了 redis 中的值反序列化成对象\n词库解析（目前仅支持搜狗词库）\n使用 WEB 功能，如：响应头注解、缓存头注解、兼容性获取用户端真实 IP、获取用户真实 IP 注解\n替代 springfamework 5，支持 apache velocity\n基于 maxmind geoip 的 IP 信息解析\n基于标准的 HTTP 请求方法的 HttpClient\n文件操作，如：写文件、设置文件所属用户或组、文件 MimeType 解析\n... ...它是同类开源框架的一种兼容性的上层封装，简化框架切换带来的成本摒弃直接使用原生类库，带来的大量的代码修改，如：HttpClient 支持 apache httpcomponents 和 okhttp3，只需要修改 HttpClient 初始化类，即可实现 HTTP 库的切换\n... ..."
        }
      ]
    },
    {
      "title": "开源协议",
      "content": "                             Apache License                       Version 2.0, January 2004\n                    http://www.apache.org/licenses/\nTERMS AND CONDITIONS FOR USE, REPRODUCTION, AND DISTRIBUTION\nDefinitions.\n\"License\" shall mean the terms and conditions for use, reproduction,\nand distribution as defined by Sections 1 through 9 of this document.\n\"Licensor\" shall mean the copyright owner or entity authorized by\nthe copyright owner that is granting the License.\n\"Legal Entity\" shall mean the union of the acting entity and all\nother entities that control, are controlled by, or are under common\ncontrol with that entity. For the purposes of this definition,\n\"control\" means (i) the power, direct or indirect, to cause the\ndirection or management of such entity, whether by contract or\notherwise, or (ii) ownership of fifty percent (50%) or more of the\noutstanding shares, or (iii) beneficial ownership of such entity.\n\"You\" (or \"Your\") shall mean an individual or Legal Entity\nexercising permissions granted by this License.\n\"Source\" form shall mean the preferred form for making modifications,\nincluding but not limited to software source code, documentation\nsource, and configuration files.\n\"Object\" form shall mean any form resulting from mechanical\ntransformation or translation of a Source form, including but\nnot limited to compiled object code, generated documentation,\nand conversions to other media types.\n\"Work\" shall mean the work of authorship, whether in Source or\nObject form, made available under the License, as indicated by a\ncopyright notice that is included in or attached to the work\n(an example is provided in the Appendix below).\n\"Derivative Works\" shall mean any work, whether in Source or Object\nform, that is based on (or derived from) the Work and for which the\neditorial revisions, annotations, elaborations, or other modifications\nrepresent, as a whole, an original work of authorship. For the purposes\nof this License, Derivative Works shall not include works that remain\nseparable from, or merely link (or bind by name) to the interfaces of,\nthe Work and Derivative Works thereof.\n\"Contribution\" shall mean any work of authorship, including\nthe original version of the Work and any modifications or additions\nto that Work or Derivative Works thereof, that is intentionally\nsubmitted to Licensor for inclusion in the Work by the copyright owner\nor by an individual or Legal Entity authorized to submit on behalf of\nthe copyright owner. For the purposes of this definition, \"submitted\"\nmeans any form of electronic, verbal, or written communication sent\nto the Licensor or its representatives, including but not limited to\ncommunication on electronic mailing lists, source code control systems,\nand issue tracking systems that are managed by, or on behalf of, the\nLicensor for the purpose of discussing and improving the Work, but\nexcluding communication that is conspicuously marked or otherwise\ndesignated in writing by the copyright owner as \"Not a Contribution.\"\n\"Contributor\" shall mean Licensor and any individual or Legal Entity\non behalf of whom a Contribution has been received by Licensor and\nsubsequently incorporated within the Work.\n\n\nGrant of Copyright License. Subject to the terms and conditions of\nthis License, each Contributor hereby grants to You a perpetual,\nworldwide, non-exclusive, no-charge, royalty-free, irrevocable\ncopyright license to reproduce, prepare Derivative Works of,\npublicly display, publicly perform, sublicense, and distribute the\nWork and such Derivative Works in Source or Object form.\n\n\nGrant of Patent License. Subject to the terms and conditions of\nthis License, each Contributor hereby grants to You a perpetual,\nworldwide, non-exclusive, no-charge, royalty-free, irrevocable\n(except as stated in this section) patent license to make, have made,\nuse, offer to sell, sell, import, and otherwise transfer the Work,\nwhere such license applies only to those patent claims licensable\nby such Contributor that are necessarily infringed by their\nContribution(s) alone or by combination of their Contribution(s)\nwith the Work to which such Contribution(s) was submitted. If You\ninstitute patent litigation against any entity (including a\ncross-claim or counterclaim in a lawsuit) alleging that the Work\nor a Contribution incorporated within the Work constitutes direct\nor contributory patent infringement, then any patent licenses\ngranted to You under this License for that Work shall terminate\nas of the date such litigation is filed.\n\n\nRedistribution. You may reproduce and distribute copies of the\nWork or Derivative Works thereof in any medium, with or without\nmodifications, and in Source or Object form, provided that You\nmeet the following conditions:\n(a) You must give any other recipients of the Work or\nDerivative Works a copy of this License; and\n(b) You must cause any modified files to carry prominent notices\nstating that You changed the files; and\n(c) You must retain, in the Source form of any Derivative Works\nthat You distribute, all copyright, patent, trademark, and\nattribution notices from the Source form of the Work,\nexcluding those notices that do not pertain to any part of\nthe Derivative Works; and\n(d) If the Work includes a \"NOTICE\" text file as part of its\ndistribution, then any Derivative Works that You distribute must\ninclude a readable copy of the attribution notices contained\nwithin such NOTICE file, excluding those notices that do not\npertain to any part of the Derivative Works, in at least one\nof the following places: within a NOTICE text file distributed\nas part of the Derivative Works; within the Source form or\ndocumentation, if provided along with the Derivative Works; or,\nwithin a display generated by the Derivative Works, if and\nwherever such third-party notices normally appear. The contents\nof the NOTICE file are for informational purposes only and\ndo not modify the License. You may add Your own attribution\nnotices within Derivative Works that You distribute, alongside\nor as an addendum to the NOTICE text from the Work, provided\nthat such additional attribution notices cannot be construed\nas modifying the License.\nYou may add Your own copyright statement to Your modifications and\nmay provide additional or different license terms and conditions\nfor use, reproduction, or distribution of Your modifications, or\nfor any such Derivative Works as a whole, provided Your use,\nreproduction, and distribution of the Work otherwise complies with\nthe conditions stated in this License.\n\n\nSubmission of Contributions. Unless You explicitly state otherwise,\nany Contribution intentionally submitted for inclusion in the Work\nby You to the Licensor shall be under the terms and conditions of\nthis License, without any additional terms or conditions.\nNotwithstanding the above, nothing herein shall supersede or modify\nthe terms of any separate license agreement you may have executed\nwith Licensor regarding such Contributions.\n\n\nTrademarks. This License does not grant permission to use the trade\nnames, trademarks, service marks, or product names of the Licensor,\nexcept as required for reasonable and customary use in describing the\norigin of the Work and reproducing the content of the NOTICE file.\n\n\nDisclaimer of Warranty. Unless required by applicable law or\nagreed to in writing, Licensor provides the Work (and each\nContributor provides its Contributions) on an \"AS IS\" BASIS,\nWITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or\nimplied, including, without limitation, any warranties or conditions\nof TITLE, NON-INFRINGEMENT, MERCHANTABILITY, or FITNESS FOR A\nPARTICULAR PURPOSE. You are solely responsible for determining the\nappropriateness of using or redistributing the Work and assume any\nrisks associated with Your exercise of permissions under this License.\n\n\nLimitation of Liability. In no event and under no legal theory,\nwhether in tort (including negligence), contract, or otherwise,\nunless required by applicable law (such as deliberate and grossly\nnegligent acts) or agreed to in writing, shall any Contributor be\nliable to You for damages, including any direct, indirect, special,\nincidental, or consequential damages of any character arising as a\nresult of this License or out of the use or inability to use the\nWork (including but not limited to damages for loss of goodwill,\nwork stoppage, computer failure or malfunction, or any and all\nother commercial damages or losses), even if such Contributor\nhas been advised of the possibility of such damages.\n\n\nAccepting Warranty or Additional Liability. While redistributing\nthe Work or Derivative Works thereof, You may choose to offer,\nand charge a fee for, acceptance of support, warranty, indemnity,\nor other liability obligations and/or rights consistent with this\nLicense. However, in accepting such obligations, You may act only\non Your own behalf and on Your sole responsibility, not on behalf\nof any other Contributor, and only if You agree to indemnify,\ndefend, and hold each Contributor harmless for any liability\nincurred by, or claims asserted against, such Contributor by reason\nof your accepting any such warranty or additional liability.\n\nEND OF TERMS AND CONDITIONSAPPENDIX: How to apply the Apache License to your work.  To apply the Apache License to your work, attach the following  boilerplate notice, with the fields enclosed by brackets \"[]\"\n  replaced with your own identifying information. (Don't include\n  the brackets!)  The text should be enclosed in the appropriate\n  comment syntax for the file format. We also recommend that a\n  file or class name and description of purpose be included on the\n  same \"printed page\" as the copyright notice for easier\n  identification within third-party archives.\nCopyright [yyyy] [name of copyright owner]Licensed under the Apache License, Version 2.0 (the \"License\");you may not use this file except in compliance with the License.\nYou may obtain a copy of the License at   http://www.apache.org/licenses/LICENSE-2.0Unless required by applicable law or agreed to in writing, softwaredistributed under the License is distributed on an \"AS IS\" BASIS,\nWITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\nSee the License for the specific language governing permissions and\nlimitations under the License.",
      "url": "/docs/license.html",
      "children": []
    },
    {
      "title": "模块说明",
      "content": "",
      "url": "/docs/module.html",
      "children": [
        {
          "title": "buession-aop",
          "url": "/docs/module.html#buession-aop",
          "content": "buession-aopAOP 封装，方便实现自定义注解\n"
        },
        {
          "title": "buession-beans",
          "url": "/docs/module.html#buession-beans",
          "content": "buession-beansBean 工具类封\n"
        },
        {
          "title": "buession-core",
          "url": "/docs/module.html#buession-core",
          "content": "buession-core一些继承 apache lang3、apache collections4 的对字符串、集合、List、Map、Class、Object、Enum、Number等工具类封装和扩展\n汉字拼音工具类\n版本对比工具类\n数学计算\nIP 地址工具类\n进制转换\n对象序列化和反序列化，支持二进制、FastJson、Gson、Jackson\n数据合法性验证类\n带 code 和 message 消息消息对象，通过 properties 的形式注入\n日期对象类\nID 生成器\nManager 层注解\n"
        },
        {
          "title": "buession-cron",
          "url": "/docs/module.html#buession-cron",
          "content": "buession-cron对 quartz 的二次封装\n"
        },
        {
          "title": "buession-dao",
          "url": "/docs/module.html#buession-dao",
          "content": "buession-dao对 mybatis、spring-data-mongo 常用方法（如：根据条件获取单条记录、根据主键获取单条记录、分页、根据条件删除数据、根据主键删除数据）进行了二次封装\n从代码层面上支持数据库一主多从实现读写分离，insert、update、delete 操作主库，select 操作从库\n"
        },
        {
          "title": "buession-geoip",
          "url": "/docs/module.html#buession-geoip",
          "content": "buession-geoip对 com.maxmind.geoip2:geoip2 进行二次封装，实现支持根据 IP 地址获取所属 ISP、所属国家、所属城市等等信息\n"
        },
        {
          "title": "buession-httpclient",
          "url": "/docs/module.html#buession-httpclient",
          "content": "buession-httpclient对 apache httpcomponents、okhttp3 进行封装，屏蔽了 apache httpcomponents 和 okhttp3 的不同技术细节\n屏蔽了对 post form、post json 等等的技术细节\n"
        },
        {
          "title": "buession-io",
          "url": "/docs/module.html#buession-io",
          "content": "buession-io封装了对文件的操作\n"
        },
        {
          "title": "buession-jdbc",
          "url": "/docs/module.html#buession-jdbc",
          "content": "buession-jdbcJDBC 通用 POJO 类定义\n对 Hikari、Dbcp2、Druid 等配置和数据源的封装\n"
        },
        {
          "title": "buession-json",
          "url": "/docs/module.html#buession-json",
          "content": "buession-json主要实现了一些 jackson 的自定义注解及序列化、反序列化的实现\n"
        },
        {
          "title": "buession-lang",
          "url": "/docs/module.html#buession-lang",
          "content": "buession-lang常用枚举（如：状态-Status、性别-Gender 等）的定义\n常用 POJO 类（如：地理位置-Geo、Key Value-KeyValue 等）的定义\n"
        },
        {
          "title": "buession-net",
          "url": "/docs/module.html#buession-net",
          "content": "buession-net网络相关工具类\n"
        },
        {
          "title": "buession-redis",
          "url": "/docs/module.html#buession-redis",
          "content": "buession-redisRedis 操作类，基于 jedis 实现，RedisTemplate 方法名、参数几乎同 redis 命令保持一致\n对对象读写 redis 进行了扩展，支持二进制、json方式序列化和反序列化，例如：通过 RedisTemplate.getObject(“key”, Class) 可以将 redis 中的数据读取出来后，直接反序列化成一个对象\n"
        },
        {
          "title": "buession-session",
          "url": "/docs/module.html#buession-session",
          "content": "buession-session...\n"
        },
        {
          "title": "buession-thesaurus",
          "url": "/docs/module.html#buession-thesaurus",
          "content": "buession-thesaurus对词库的解析，目前仅支持搜狗词条\n"
        },
        {
          "title": "buession-velocity",
          "url": "/docs/module.html#buession-velocity",
          "content": "buession-velocityspring mvc 不再支持 velocity，这里主要是把原来 spring mvc 中关于 velocity 的实现迁移过来，让喜欢 velocity 的 coder 继续在高版本的 springframework 中继续使用 velocity\n"
        },
        {
          "title": "buession-web",
          "url": "/docs/module.html#buession-web",
          "content": "buession-webweb 相关的功能封装，支持 servlet 和 reactive\n封装了一些常用注解，简化了业务层方面的代码实现\n封装了一些常用 filter\n"
        }
      ]
    },
    {
      "title": "版本说明",
      "content": "该项目基于 GNU 版风格定义项目版本，即：主版本号.子版本号.修正版本号。管理策略主版本号，发生变更时，不保证所有的 API 对上一个版本兼容，但保障大部分能兼容；主版本变更，可能涉及类、接口、枚举、方法的删除，或者包名的变更\n子版本号，发生变更时，完全兼容上一个版本，主要会增加一些小的功能或API，底层逻辑的调整调优\n修正版本号，主要用于修复 BUG、优化性能、安全漏洞修复，不会新增、变更、删除已有 API\n三方包兼容性说明当引用的三方包，我们保证尽大可能兼容。但对于 springframework、springboot、springcloud、springsecurity、springdata 等 spring 家族组件，以及 servlet 兼容对应的主版本。",
      "url": "/docs/version.html",
      "children": []
    },
    {
      "title": "安装及使用",
      "content": "",
      "url": "/docs/installation.html",
      "children": [
        {
          "title": "Maven 中央仓库搜索",
          "url": "/docs/installation.html#maven-中央仓库搜索",
          "content": "Maven 中央仓库搜索https://mvnrepository.com/search?q=com.buession\nhttps://search.maven.org/search?q=g:com.buession\n"
        },
        {
          "title": "手动编译",
          "url": "/docs/installation.html#手动编译",
          "content": "手动编译git clone https://github.com/buession/buessionframeworkcd buessionframework/buession-parent && mvn clean install\n"
        },
        {
          "title": "Maven",
          "url": "/docs/installation.html#maven",
          "content": "Maven    com.buession\n    buession-xxx\n    x.x.x\n\n"
        },
        {
          "title": "Gradle",
          "url": "/docs/installation.html#gradle",
          "content": "Gradlecompile group: 'com.buession', name: 'buession-xxx', version: 'x.x.x'其中，artifactId 中的 xxx 表示对应的子模块；version 中的 x.x.x 代表版本号，根据需要使用特定版本，建议使用 maven 仓库中已构建好的最新版本的包。"
        }
      ]
    },
    {
      "title": "环境要求",
      "content": "JDKJDK 8+构建工具\n\n构建工具\n版本\n\n\n\n\nMaven\n3.5+\n\n\nGradle\n6.x+，推荐 6.3 及以上版本\n\n\nServlet 容器支持 servlet 3.1+，推荐使用 servlet 4.0 及以上版本。",
      "url": "/docs/requirement.html",
      "children": []
    },
    {
      "title": "下一步计划",
      "content": "下一步计划，在 2.0.x 版本中，完善代码中的注释，便于生成的 javadoc 利于查阅，同步完善官网文档\n同时，对现有代码中存在的 BUG 进行修复，和代码层面的优化\n三方类库的小版本升级，在保证兼容性的前提下，仅大可能的通过升级三方依赖包的版本来修复其已知安全漏洞\n在 2.0.x 不会增加新的功能特性\n整个，大致时间计划，在2022国庆前后，终止 2.0.x 版本的开发。",
      "url": "/docs/plan.html",
      "children": []
    }
  ],
  "参考手册": [
    {
      "title": "参考手册简介",
      "content": "Buession Framework 它是日常工作中常见的通用技术需求二次封装，提供了众多常用的类库、方法、注解；同时基于 springfrawork、jsckson、jedis、apache httpcomponents、okhttp3 等等众多的优秀的三方工具的标准化的、统一的类库的上层封装，简化框架切换带来的成本。更多介绍开源查阅框架介绍。本章节将想您讲解，如何使用 Buession Framework，为您提供 Java 应用的最佳实践。",
      "url": "/manual/index.html",
      "children": []
    },
    {
      "title": "参考指南",
      "content": "本文档包含了完整的 Buession Logging 的参考文档。\n\n版本\n手册\n\n\n\n\n1.0.x\nAPI 手册\n\n\n",
      "url": "/manual/overview.html",
      "children": []
    },
    {
      "title": "API 参考手册",
      "content": "Buession Framework API 包含以下目录：\n\n模块\n使用帮助\n手册\n\n\n\n\nbuession-aop\n使用帮助\nAPI 手册\n\n\nbuession-beans\n使用帮助\nAPI 手册\n\n\nbuession-core\n使用帮助\nAPI 手册\n\n\nbuession-cron\n使用帮助\nAPI 手册\n\n\nbuession-dao\n使用帮助\nAPI 手册\n\n\nbuession-geoip\n使用帮助\nAPI 手册\n\n\nbuession-httpclient\n使用帮助\nAPI 手册\n\n\nbuession-io\n使用帮助\nAPI 手册\n\n\nbuession-jdbc\n使用帮助\nAPI 手册\n\n\nbuession-json\n使用帮助\nAPI 手册\n\n\nbuession-lang\n使用帮助\nAPI 手册\n\n\nbuession-net\n使用帮助\nAPI 手册\n\n\nbuession-redis\n使用帮助\nAPI 手册\n\n\nbuession-session\n使用帮助\nAPI 手册\n\n\nbuession-thesaurus\n使用帮助\nAPI 手册\n\n\nbuession-velocity\n使用帮助\nAPI 手册\n\n\nbuession-web\n使用帮助\nAPI 手册\n\n\n",
      "url": "/manual/1.0/index.html",
      "children": []
    },
    {
      "title": "buession-aop 参考手册",
      "content": "AOP 封装，方便实现自定义注解",
      "url": "/manual/1.0/annotations/index.html",
      "children": [
        {
          "title": "安装",
          "url": "/manual/1.0/annotations/index.html#安装",
          "content": "安装    com.buession\n    buession-aop\n    x.x.x\n\n"
        },
        {
          "title": "API 参考手册>>",
          "url": "/manual/1.0/annotations/index.html#api-参考手册>>",
          "content": "API 参考手册>>"
        }
      ]
    },
    {
      "title": "buession-beans 参考手册",
      "content": "该类库提供了基于 commons-beanutils 和 cglib（spring-core 包中，名称空间：org.springframework.cglib.beans） 的 bean 工具的二次封装。包括了属性拷贝和属性映射，以及对象属性转换为 Map。",
      "url": "/manual/1.0/aspectj/index.html",
      "children": [
        {
          "title": "安装",
          "url": "/manual/1.0/aspectj/index.html#安装",
          "content": "安装    com.buession\n    buession-beans\n    x.x.x\n\n"
        },
        {
          "title": "属性拷贝",
          "url": "/manual/1.0/aspectj/index.html#属性拷贝",
          "content": "属性拷贝使用此方法，可以实现两个对象之间的属性拷贝，该方式基于 BeanCopier 实现属性的拷贝。如果 source 对象是 Map 接口的实现，则会将 Map 的 key 和 target 的属性做映射，实现同名拷贝。import com.buession.beans.BeanUtils;\nBeanUtils.copyProperties(target, source)\n注：该方式只会对同类型的属性进行拷贝。即假设，source 中有数据类型为 int 名称为 id 的属性，target 中有数据类型为 long 名称为 id 的属性，是不会将 source 属性 id 的值拷贝到 target 的 id 属性上。\n我们可以指定 Converter 实现自定义规则进行属性拷贝。该方式的缺点是：BeanCopier 只使用 Converter 定义的规则去拷贝属性，所以在 convert 方法中要考虑所有的属性import com.buession.beans.BeanUtils;import org.springframework.cglib.core.Converter;\n\nBeanUtils.copyProperties(target, source, new Converter() {\n\n\t@Override\n\tpublic Object convert(Object sourceFieldValue, Class targetFieldType, Object targetSetter){\n\t\tif(sourceFieldValue instanceof Short || sourceFieldValue instanceof Integer){\n\t\t\tif(targetFieldType.isAssignableFrom(Long.class) || targetFieldType.isAssignableFrom(long.class)){\n\t\t\t\treturn sourceFieldValue;\n\t\t\t}\n\t\t}else if(sourceFieldValue.getClass().isAssignableFrom(targetFieldType)){\n\t\t\treturn sourceFieldValue;\n\t\t}\n\n\t\treturn null;\n\t}\n\n});\n"
        },
        {
          "title": "属性映射",
          "url": "/manual/1.0/aspectj/index.html#属性映射",
          "content": "属性映射使用此方法，可以实现两个对象之间的属性拷贝，该方式基于 BeanCopier 实现属性的拷贝。如果 source 对象是 Map 接口的实现，则会将 Map 的 key 转换为驼峰格式后和 target 的属性做映射，实现拷贝，这是 populate 和 copyProperties 的唯一区别。import com.buession.beans.BeanUtils;\nBeanUtils.populate(target, source)\n注：该方式只会对同类型的属性进行拷贝。即假设，source 中有数据类型为 int 名称为 id 的属性，target 中有数据类型为 long 名称为 id 的属性，是不会将 source 属性 id 的值拷贝到 target 的 id 属性上。\n我们可以指定 Converter 实现自定义规则进行属性拷贝。该方式的缺点是：BeanCopier 只使用 Converter 定义的规则去拷贝属性，所以在 convert 方法中要考虑所有的属性import com.buession.beans.BeanUtils;import org.springframework.cglib.core.Converter;\n\nBeanUtils.populate(target, source, new Converter() {\n\n\t@Override\n\tpublic Object convert(Object sourceFieldValue, Class targetFieldType, Object targetSetter){\n\t\tif(sourceFieldValue instanceof Short || sourceFieldValue instanceof Integer){\n\t\t\tif(targetFieldType.isAssignableFrom(Long.class) || targetFieldType.isAssignableFrom(long.class)){\n\t\t\t\treturn sourceFieldValue;\n\t\t\t}\n\t\t}else if(sourceFieldValue.getClass().isAssignableFrom(targetFieldType)){\n\t\t\treturn sourceFieldValue;\n\t\t}\n\n\t\treturn null;\n\t}\n\n});\n"
        },
        {
          "title": "Bean 转换为 Map",
          "url": "/manual/1.0/aspectj/index.html#bean-转换为-map",
          "content": "Bean 转换为 Map使用此方法，可以实现将一个 bean 对象转换为 Map，bean 的属性作为 Map 的 Keyimport com.buession.beans.BeanUtils;\nMap result = BeanUtils.toMap(bean)\n"
        },
        {
          "title": "API 参考手册>>",
          "url": "/manual/1.0/aspectj/index.html#api-参考手册>>",
          "content": "API 参考手册>>"
        }
      ]
    },
    {
      "title": "buession-core 参考手册",
      "content": "该类库为核心包，包括构建器、工具类、数据验证、ID 生成器、转换器、序列化、数学方法、消息注入、Manager 层注解、日期时间类和各通用接口定义。",
      "url": "/manual/1.0/core/index.html",
      "children": [
        {
          "title": "安装",
          "url": "/manual/1.0/core/index.html#安装",
          "content": "安装    com.buession\n    buession-core\n    x.x.x\n\n构建器Map、集合的便捷式构建，减少您的代码行数编码器目前，仅包含消息构建器，您可以通过注解 @Message 将您环境中的错误码、错误消息的配置注入到 MessageObject 类型的属性中收集器数组、Map、集合的工具类上下文定义应用上下文的类库、注解转换器数据转化器，基于 mapstruct 的 POJO 类映射接口定义。定义了常用的数据转化器。日期时间日期、时间工具ID 生成器基于 UUID、jnanoid ID、雪花算法等等的 ID 生成器。数学函数定义了实用的数学函数序列化和反序列化对象的序列化和反序列化，包括二进制和 JSON。验证器数据验证器及其注解工具类常用通用性工具类其它通用的接口定义，框架自身类异常通用异常的定义"
        },
        {
          "title": "API 参考手册>>",
          "url": "/manual/1.0/core/index.html#api-参考手册>>",
          "content": "API 参考手册>>"
        }
      ]
    },
    {
      "title": "buession-cron 参考手册",
      "content": "对 quartz 的二次封装",
      "url": "/manual/1.0/support-core/index.html",
      "children": [
        {
          "title": "安装",
          "url": "/manual/1.0/support-core/index.html#安装",
          "content": "安装    com.buession\n    buession-cron\n    x.x.x\n\n由于在过去的工作中，定时任务基本使用 quartz 来实现；但是在初始化定时任务项目时，大量基本相同的代码，因此对此部分做了二次封装，简化了 quartz 项目的初始化。由于在现在有众多优秀的分布式定时任务，如：elastic-job、xxl-job 等等，因此直接使用 quartz 应该会越来越少（个人主观猜测），即使使用 quartz 初始化也简单，故该模块将不做说明。且在今后的版本中，该模块可能会被废弃。"
        },
        {
          "title": "API 参考手册>>",
          "url": "/manual/1.0/support-core/index.html#api-参考手册>>",
          "content": "API 参考手册>>"
        }
      ]
    },
    {
      "title": "buession-dao 参考手册",
      "content": "对 mybatis、spring-data-mongo 常用方法（如：根据条件获取单条记录、根据主键获取单条记录、分页、根据条件删除数据、根据主键删除数据）进行了二次封装。从代码层面实现了数据库的读写分离，insert、update、delete 操作主库，select 操作从库。",
      "url": "/manual/1.0/support-console/index.html",
      "children": [
        {
          "title": "安装",
          "url": "/manual/1.0/support-console/index.html#安装",
          "content": "安装    com.buession\n    buession-dao\n    x.x.x\n\n我们咋众多项目中，基本有常见的重复的对数据库的 CURD 操作，比如：根据主键查询数据、根据主键删除数据、获取一条记录。MyBatis 是一款优秀的持久层框架，应用广泛。MongoDB 是一款优秀的文档数据库。我自己根据从业多年的经验，从实际场合出发，将在业务层对数据库的常用操作进行了封装。对关系型数据库基于 MyBatis 二次封装，对 MongoDB 基于 spring-data-mongodb；在未来也许会考虑，增加 jpa 和 JdbcTemplate 对关系型数据库的二次封装。同时，我们在代码层面实现了数据库的读写分离。我们没有改变 MyBatis 和 spring-data-mongodb 的任何底层逻辑，本质就是 MyBaits 和 spring-data-mongodb；我们唯一做了的就是，定义和是了大家在应用程序中常用的方法，让您不在重复去编写该部分代码；以及在代码层面实现了数据的读写分离。"
        },
        {
          "title": "Dao 接口",
          "url": "/manual/1.0/support-console/index.html#dao-接口",
          "content": "Dao 接口接口定义，可见：https://javadoc.io/static/com.buession/buession-dao/2.0.2/com/buession/dao/Dao.htmlpublic interface Dao {}\nP：主键类型\nE：实体类\n分页对象 com.buession.dao.Pagination 继承自 com.buession.core.Pagination，增加了偏移量属性 offset。条件为 Map 类型，允许为 null。排序为 Map 类型，允许为 null。MyBatisBuession Framework 扩展 MyBatis 的文档。MongoDBBuession Framework 扩展 spring-data-mongodb 的文档。"
        },
        {
          "title": "API 参考手册>>",
          "url": "/manual/1.0/support-console/index.html#api-参考手册>>",
          "content": "API 参考手册>>"
        }
      ]
    },
    {
      "title": "buession-geoip 参考手册",
      "content": "对 com.maxmind.geoip2:geoip2 进行二次封装，实现支持根据 IP 地址获取所属 ISP、所属国家、所属城市等等信息。",
      "url": "/manual/1.0/support-elasticsearch/index.html",
      "children": [
        {
          "title": "安装",
          "url": "/manual/1.0/support-elasticsearch/index.html#安装",
          "content": "安装    com.buession\n    buession-geoip\n    x.x.x\n\n通常我们在应用中对用户注册、登录、以及其它操作记录 IP，我们更希望知道用户在什么城市进行的操作，如：微信公众号的内容发表于、微博的发布于等等，对于用户行为的安全审计等等有着极高的作用。geoip 在基于 maxmind geoip2 的基础上进行了二次封装，可以根据 IP（字符串形式的 IP，如：114.114.114.114、2001:0DB8:0000:0023:0008:0800:200C:417A ，IPV4 的数字表示：3739974408，java InetAddress）获取其 IP 地址的国家信息、城市信息、位置信息。"
        },
        {
          "title": "获取国家信息",
          "url": "/manual/1.0/support-elasticsearch/index.html#获取国家信息",
          "content": "获取国家信息import com.buession.geoip.model.Country;import com.buession.geoip.model.DatabaseResolver;\n\nDatabaseResolver resolver = new DatabaseResolver(DatabaseResolver.class.getResourceAsStream(\"/maxmind/City.mmdb\"));\nCountry country = resolver.country(\"114.114.114.114\");\n// Country{geoNameId=1814991, confidence=null, code='CN', originalName='China', name='中国', fullName='中华人民共和国', isInEuropeanUnion=false}\n\nCountry country = resolver.country(3739974408L); // 3739974408L => 222.235.123.8\n// Country{geoNameId=1835841, confidence=null, code='KR', originalName='Republic of Korea', name='大韩民国', fullName='大韩民国', isInEuropeanUnion=false}\n"
        },
        {
          "title": "获取城市信息",
          "url": "/manual/1.0/support-elasticsearch/index.html#获取城市信息",
          "content": "获取城市信息import com.buession.geoip.model.Country;import com.buession.geoip.model.DatabaseResolver;\n\nDatabaseResolver resolver = new DatabaseResolver(DatabaseResolver.class.getResourceAsStream(\"/maxmind/City.mmdb\"));\nDistrict district = resolver.district(\"114.114.114.114\");\n// District{geoNameId=1799962, code=null, originalName='Nanjing', name='南京', fullName='江苏省南京', postal=Postal{code='null', confidence=null}, parent=District{geoNameId=1806260, code=null, originalName='Jiangsu', name='江苏省', fullName='江苏省江苏省', postal=null, parent=District{geoNameId=1806260, code=null, originalName='Jiangsu', name='江苏省', fullName='江苏省', postal=null, parent=null}}}\n\nDistrict district = resolver.district(3739974408L); // 3739974408L => 222.235.123.8\n// District{geoNameId=1835848, code=null, originalName='Seoul', name='首尔特别市', fullName='首尔特别市首尔特别市', postal=Postal{code='null', confidence=null}, parent=District{geoNameId=1835847, code=null, originalName='Seoul', name='首尔特别市', fullName='首尔特别市首尔特别市', postal=null, parent=District{geoNameId=1835847, code=null, originalName='Seoul', name='首尔特别市', fullName='首尔特别市', postal=null, parent=null}}}\n"
        },
        {
          "title": "获取位置信息",
          "url": "/manual/1.0/support-elasticsearch/index.html#获取位置信息",
          "content": "获取位置信息位置信息中包括了该 IP 比较全面的信息，包括：城市信息、国家信息、洲信息、经纬度、机构信息、时区等。import com.buession.geoip.model.Country;import com.buession.geoip.model.DatabaseResolver;\n\nDatabaseResolver resolver = new DatabaseResolver(DatabaseResolver.class.getResourceAsStream(\"/maxmind/City.mmdb\"));\nLocation location = resolver.location(\"114.114.114.114\");\n// Location{continent=Continent{geoNameId=6255147, code='AS', originalName='Asia', name='亚洲'}, country=Country{geoNameId=1814991, confidence=null, code='CN', originalName='China', name='中国', fullName='中华人民共和国', isInEuropeanUnion=false}, district=District{geoNameId=1799962, code=null, originalName='Nanjing', name='南京', fullName='江苏省南京', postal=Postal{code='null', confidence=null}, parent=District{geoNameId=1806260, code=null, originalName='Jiangsu', name='江苏省', fullName='江苏省江苏省', postal=null, parent=District{geoNameId=1806260, code=null, originalName='Jiangsu', name='江苏省', fullName='江苏省', postal=null, parent=null}}}, traits=Traits{ipAddress='114.114.114.114', domain='null', isp='null', network=114.114.0.0/16, connectionType=null, organization=null, autonomousSystemOrganization=null, autonomousSystemNumber=null, isAnonymous=false, isAnonymousProxy=false, isAnonymousVpn=false, isHostingProvider=false, isLegitimateProxy=false, isPublicProxy=false, isSatelliteProvider=false, isTorExitNode=false, userType='false', userCount=null, staticIpScore=null}, geo=Geo{latitude=32.0617, longitude=118.7778, accuracyRadius=50}, timeZone=sun.util.calendar.ZoneInfo[id=\"Asia/Shanghai\",offset=28800000,dstSavings=0,useDaylight=false,transitions=31,lastRule=null]}\n\nLocation location = resolver.location(3739974408L); // 3739974408L => 222.235.123.8\n// Location{continent=Continent{geoNameId=6255147, code='AS', originalName='Asia', name='亚洲'}, country=Country{geoNameId=1835841, confidence=null, code='KR', originalName='Republic of Korea', name='大韩民国', fullName='大韩民国', isInEuropeanUnion=false}, district=District{geoNameId=1835848, code=null, originalName='Seoul', name='首尔特别市', fullName='首尔特别市首尔特别市', postal=Postal{code='null', confidence=null}, parent=District{geoNameId=1835847, code=null, originalName='Seoul', name='首尔特别市', fullName='首尔特别市首尔特别市', postal=null, parent=District{geoNameId=1835847, code=null, originalName='Seoul', name='首尔特别市', fullName='首尔特别市', postal=null, parent=null}}}, traits=Traits{ipAddress='222.235.123.8', domain='null', isp='null', network=222.235.120.0/21, connectionType=null, organization=null, autonomousSystemOrganization=null, autonomousSystemNumber=null, isAnonymous=false, isAnonymousProxy=false, isAnonymousVpn=false, isHostingProvider=false, isLegitimateProxy=false, isPublicProxy=false, isSatelliteProvider=false, isTorExitNode=false, userType='false', userCount=null, staticIpScore=null}, geo=Geo{latitude=37.5111, longitude=126.9743, accuracyRadius=200}, timeZone=sun.util.calendar.ZoneInfo[id=\"Asia/Seoul\",offset=32400000,dstSavings=0,useDaylight=false,transitions=30,lastRule=null]}\n"
        },
        {
          "title": "缓存",
          "url": "/manual/1.0/support-elasticsearch/index.html#缓存",
          "content": "缓存为了提高数据的处理能力，可以将获取过的数据缓存起来，下次获取同一 IP 信息时，可以直接从缓存中返回。您可以通过 DatabaseResolver 构造函数中的参数 cache 设置为 com.maxmind.db.NodeCache 的实现类即可，或直接使用类 CacheDatabaseResolver解析。我们默认使用 maxmind 内置的 CHMCache 来实现缓存，它是基于 ConcurrentHashMap 的内存缓存。"
        },
        {
          "title": "Resolver 的 Spring Factory Bean",
          "url": "/manual/1.0/support-elasticsearch/index.html#resolver-的-spring-factory-bean",
          "content": "Resolver 的 Spring Factory Bean我们内置了 geoip 的 Resolver spring factory bean 类 GeoIPResolverFactoryBean，您可以通过它在您的 spring 项目中，初始化 Resolver 的实现类为 spring bean 对象。dbPath 和 stream 二选一即可，一个是指定 IP 的文件路径，一个是指定已加载的 IP 库的文件流。都不设置的默认以流的形式加载 buession-geoip 中的 IP 库文件。\nenableCache 可以控制是否缓存。\n"
        },
        {
          "title": "关于 IP 库",
          "url": "/manual/1.0/support-elasticsearch/index.html#关于-ip-库",
          "content": "关于 IP 库buession-geoip 中包含了 maxmind 免费的 IP 所属城市和国家的库。由于在 jar 包中该数据库无法做到及时更新，在实际应用中，我们建议您从 maxmind 官网下载 IP 方法您的应用中，通过 DatabaseResolver 的构造函数指定 IP 库路径，这么做的好处是：在您的应用程序中，可以去保证 IP 库是更新的。"
        },
        {
          "title": "API 参考手册>>",
          "url": "/manual/1.0/support-elasticsearch/index.html#api-参考手册>>",
          "content": "API 参考手册>>"
        }
      ]
    },
    {
      "title": "buession-httpclient 参考手册",
      "content": "对 apache httpcomponents、okhttp3 进行封装，屏蔽了 apache httpcomponents 和 okhttp3 的不同技术细节，屏蔽了对 post form、post json 等等的技术细节。",
      "url": "/manual/1.0/support-file/index.html",
      "children": [
        {
          "title": "安装",
          "url": "/manual/1.0/support-file/index.html#安装",
          "content": "安装    com.buession\n    buession-httpclient\n    x.x.x\n\n我们在应用中使用 Http Client 功能时，经常因为从 apache httpcomponents 切换为 okhttp3，或者从 okhttp3 切换为 apache httpcomponents，需要改动大量的代码而烦恼。而当您使用了 buession-httpclient 时，该类库为您解决了这些烦恼，通过顶层设计，屏蔽了 apache httpcomponents 和 okhttp3 的细节，当您需要从一个 http 库更换为另外一个 http 库时，您只需要在 pom.xml 中引用不同的包，修改一下 httpclient 的初始化类和连接管理器即可。传统的方式：    org.apache.httpcomponents\n    httpcore\n    x.x.x\n\n\n    org.apache.httpcomponents\n    httpclient\n    x.x.x\n\nimport org.apache.http.HttpResponse;import org.apache.http.conn.HttpClientConnectionManager;\nimport org.apache.http.client.HttpClient;\nimport org.apache.http.impl.client.HttpClientBuilder;\nimport org.apache.http.client.methods.HttpPost;\n\nHttpClient httpClient = HttpClientBuilder.create().setConnectionManager(new HttpClientConnectionManager()).build();\n\nHttpResponse response = httpClient.execute(new HttpPost(\"https://www.buession.com/\"));\n或者    com.squareup.okhttp3\n    okhttp\n    x.x.x\n\nimport okhttp3.HttpClientConnectionManager;import okhttp3.OkHttpClient;\nimport okhttp3.ConnectionPool;\nimport okhttp3.Request;\nimport okhttp3.Request.Builder;\nimport okhttp3.Response;\n\nOkHttpClient.Builder builder = new OkHttpClient.Builder().connectionPool(new ConnectionPool());\nHttpClient httpClient = builder.build();\n\nBuilder requestBuilder = new Builder().post();\nrequestBuilder.url(\"https://www.buession.com/\");\nRequest okHttpRequest = requestBuilder.build();\n\nResponse httpResponse = httpClient.newCall(okHttpRequest).execute();\n现在，您只需要通过 buession-httpclient，即可屏蔽其中的细节。    com.buession\n    buession-httpclient\n    x.x.x\n\n\n    org.apache.httpcomponents\n    httpcore\n    x.x.x\n\n\n    org.apache.httpcomponents\n    httpclient\n    x.x.x\n\n或者    com.buession\n    buession-httpclient\n    x.x.x\n\n\n    com.squareup.okhttp3\n    okhttp\n    x.x.x\n\nimport com.buession.httpclient.HttpClient;import com.buession.httpclient.ApacheHttpClient;\nimport com.buession.httpclient.OkHttpHttpClient;\nimport com.buession.httpclient.conn.ApacheClientConnectionManager;\nimport com.buession.httpclient.conn.OkHttpClientConnectionManager;\nimport com.buession.httpclient.core.Response;\n\nHttpClient httpClient = new ApacheHttpClient(new ApacheClientConnectionManager()); // 或者 new OkHttpHttpClient(new OkHttpClientConnectionManager());\n\nResponse response = httpClient.post(\"https://www.buession.com/\");\n"
        },
        {
          "title": "展望",
          "url": "/manual/1.0/support-file/index.html#展望",
          "content": "展望目前，buession-httpclient 仅支持同步，不支持异步。我们会在下一个小版本（即：2.1） 中，集成 apache httpcomponents 切换为 okhttp3 的异步 http 请求。"
        },
        {
          "title": "API 参考手册>>",
          "url": "/manual/1.0/support-file/index.html#api-参考手册>>",
          "content": "API 参考手册>>"
        }
      ]
    },
    {
      "title": "buession-io 参考手册",
      "content": "封装了对文件的操作",
      "url": "/manual/1.0/support-jdbc/index.html",
      "children": [
        {
          "title": "安装",
          "url": "/manual/1.0/support-jdbc/index.html#安装",
          "content": "安装    com.buession\n    buession-io\n    x.x.x\n\n该模块二次封装了 java java.io.File 和 java.nio.file.Files 类，在此基础上扩展了大量的实用方法，如：文件读写、获取文件 MD5 和 SHA1 值，获取文件 MIME，设置文件所属用户和用户组，简化了我们在应用开发过程中对文件的操作。"
        },
        {
          "title": "读取文件",
          "url": "/manual/1.0/support-jdbc/index.html#读取文件",
          "content": "读取文件import com.buession.io.file.File;\nFile file = new File(\"/tmp/debug.txt\");\n\nbyte[] result = file.read();\n"
        },
        {
          "title": "写文件",
          "url": "/manual/1.0/support-jdbc/index.html#写文件",
          "content": "写文件import com.buession.io.file.File;\nFile file = new File(\"/tmp/debug.txt\");\n\nfile.write(\"Buession\");\nfile.write(\"Buession\".getBytes());\nfile.write(\"Buession\", true); // 追加写\n"
        },
        {
          "title": "获取文件 MD5、SHA-1值",
          "url": "/manual/1.0/support-jdbc/index.html#获取文件-md5、sha-1值",
          "content": "获取文件 MD5、SHA-1值import com.buession.io.file.File;\nFile file = new File(\"/tmp/debug.txt\");\n\nString md5 = file.getMd5(); // 获取文件 MD5\nString sha1 = file.getSha1(); // 获取文件 SHA-1\n"
        },
        {
          "title": "获取文件 MD5、SHA-1 值",
          "url": "/manual/1.0/support-jdbc/index.html#获取文件-md5、sha-1-值",
          "content": "获取文件 MD5、SHA-1 值import com.buession.io.file.File;import com.buession.io.MimeType;\n\nFile file = new File(\"/tmp/debug.txt\");\n\nMimeType result = file.getMimeType();\n"
        },
        {
          "title": "设置文件权限",
          "url": "/manual/1.0/support-jdbc/index.html#设置文件权限",
          "content": "设置文件权限import com.buession.io.file.Files;\nFiles.chmod(\"/tmp/debug.txt\", 0777);\n"
        },
        {
          "title": "设置文件用户组",
          "url": "/manual/1.0/support-jdbc/index.html#设置文件用户组",
          "content": "设置文件用户组import com.buession.io.file.Files;\nFiles.chgrp(\"/tmp/debug.txt\", \"root\");\n"
        },
        {
          "title": "设置文件用户",
          "url": "/manual/1.0/support-jdbc/index.html#设置文件用户",
          "content": "设置文件用户import com.buession.io.file.Files;\nFiles.chown(\"/tmp/debug.txt\", \"root\");\n"
        },
        {
          "title": "注解",
          "url": "/manual/1.0/support-jdbc/index.html#注解",
          "content": "注解注解 com.buession.io.json.annotation.MimeTypeString 可以将类型为 com.buession.io.MimeType 的字段序列化为字符串和将字符串反序列化为 com.buession.io.MimeType，该功能是基于 jackson 实现的。import com.buession.io.json.annotation.MimeTypeString;\nclass File {\n\n    @MimeTypeString\n    private MimeType mime;\n\n}\n"
        },
        {
          "title": "API 参考手册>>",
          "url": "/manual/1.0/support-jdbc/index.html#api-参考手册>>",
          "content": "API 参考手册>>"
        }
      ]
    },
    {
      "title": "buession-jdbc 参考手册",
      "content": "JDBC 通用 POJO 类定义，对 Hikari、Dbcp2、Druid 等配置和数据源的封装。",
      "url": "/manual/1.0/support-kafka/index.html",
      "children": [
        {
          "title": "安装",
          "url": "/manual/1.0/support-kafka/index.html#安装",
          "content": "安装    com.buession\n    buession-jdbc\n    x.x.x\n\n通过提供的 API，您可以简化对 DBCP2、Druid、Hikari、Tomcat 数据源的初始化，该类库基本不单独使用。"
        },
        {
          "title": "API 参考手册>>",
          "url": "/manual/1.0/support-kafka/index.html#api-参考手册>>",
          "content": "API 参考手册>>"
        }
      ]
    },
    {
      "title": "buession-json 参考手册",
      "content": "主要实现了一些 jackson 的自定义注解及序列化、反序列化的实现。",
      "url": "/manual/1.0/support-mongodb/index.html",
      "children": [
        {
          "title": "安装",
          "url": "/manual/1.0/support-mongodb/index.html#安装",
          "content": "安装    com.buession\n    buession-json\n    x.x.x\n\n封装了大量基于 jackson 的注解。"
        },
        {
          "title": "注解",
          "url": "/manual/1.0/support-mongodb/index.html#注解",
          "content": "注解\n\n注解\n说明\n\n\n\n\nCalendarUnixTimestamp\njava.util.Calendar 和 Unix 时间戳序列化、反序列化；通过该注解，可以将 java.util.Calendar 序列化为 Unix 时间戳；将 Unix 时间戳反序列化为 java.util.Calendar\n\n\nDateUnixTimestamp\njava.util.Date 和 Unix 时间戳序列化、反序列化；通过该注解，可以将 java.util.Date 序列化为 Unix 时间戳；将 Unix 时间戳反序列化为 java.util.Date\n\n\nSqlDateUnixTimestamp\njava.sql.Date 和 Unix 时间戳序列化、反序列化；通过该注解，可以将 java.sql.Date 序列化为 Unix 时间戳；将 Unix 时间戳反序列化为 java.sql.Date\n\n\nTimestampUnixTimestamp\njava.sql.Timestamp 和 Unix 时间戳序列化、反序列化；通过该注解，可以将 java.sql.Timestamp 序列化为 Unix 时间戳；将 Unix 时间戳反序列化为 java.sql.Timestamp\n\n\nJsonEnum2Map\n枚举和 java.util.Map 序列化和反序列化；通过该注解，可以将枚举序列化为 java.util.Map；将 java.util.Map 反序列化为枚举\n\n\nSensitive\n通过该注解可以实现数据的脱敏\n\n\n"
        },
        {
          "title": "API 参考手册>>",
          "url": "/manual/1.0/support-mongodb/index.html#api-参考手册>>",
          "content": "API 参考手册>>"
        }
      ]
    },
    {
      "title": "buession-lang 参考手册",
      "content": "常用 POJO 类和枚举的定义，详细查看 API 参考手册。",
      "url": "/manual/1.0/support-rabbitmq/index.html",
      "children": [
        {
          "title": "安装",
          "url": "/manual/1.0/support-rabbitmq/index.html#安装",
          "content": "安装    com.buession\n    buession-lang\n    x.x.x\n\n"
        },
        {
          "title": "API 参考手册>>",
          "url": "/manual/1.0/support-rabbitmq/index.html#api-参考手册>>",
          "content": "API 参考手册>>"
        }
      ]
    },
    {
      "title": "buession-net 参考手册",
      "content": "网络相关工具类。",
      "url": "/manual/1.0/support-rest/index.html",
      "children": [
        {
          "title": "安装",
          "url": "/manual/1.0/support-rest/index.html#安装",
          "content": "安装    com.buession\n    buession-net\n    x.x.x\n\n"
        },
        {
          "title": "IP 地址工具类",
          "url": "/manual/1.0/support-rest/index.html#ip-地址工具类",
          "content": "IP 地址工具类IP 地址工具类 com.buession.net.utils.InetAddressUtis 实现了，IPV4 地址和数字型 IP 相互转换。import com.buession.net.utils.InetAddressUtis;\nlong result = InetAddressUtis.ip2long(\"127.0.0.1\"); // 2130706433\nString ip = InetAddressUtis.long2ip(2130706433L); // 127.0.0.1\nURI 类或 URIBuilder 类，实现了 url 字符串的构建，详细查看 API 手册。"
        },
        {
          "title": "API 参考手册>>",
          "url": "/manual/1.0/support-rest/index.html#api-参考手册>>",
          "content": "API 参考手册>>"
        }
      ]
    },
    {
      "title": "buession-redis 参考手册",
      "content": "Redis 操作类，基于 jedis 实现，RedisTemplate 方法名、参数几乎同 redis 原生命令保持一致。同时，对对象读写 redis 进行了扩展，支持二进制、json方式序列化和反序列化，例如：通过 RedisTemplate.getObject(“key”, Object.class) 可以将 redis 中的数据读取出来后，直接反序列化成一个对象。",
      "url": "/manual/1.0/spring/index.html",
      "children": [
        {
          "title": "安装",
          "url": "/manual/1.0/spring/index.html#安装",
          "content": "安装    com.buession\n    buession-redis\n    x.x.x\n\n"
        },
        {
          "title": "介绍",
          "url": "/manual/1.0/spring/index.html#介绍",
          "content": "介绍buession-redis 是一款基于 jedis 的 redis 操作库，最大的优势就是封装了与 redis 同名、最大程度与 redis 原生参数顺序一致的 API。同时，我们在现代应用中，经常需要读写一个 pojo 对象，buession-redis 封装了 xxxObject 读写取 redis 中的二进制或 JSON 数据，并反序列化为 POJO 类。大大简化了，我们在代码中对象存取到 redis 中，让我们更专注业务功能的开。能够通过 com.buession.redis.core.Options 设置全局选项，如：统一的 Key 前缀，对象基于什么方式序列化和反序列化。import com.buession.redis.RedisTemplate;import com.buession.redis.core.Options;\nimport com.buession.core.serializer.type.TypeReference;\nimport java.utils.Map;\nimport java.utils.HashMap;\n\nRedisTemplate redisTemplate = new RedisTemplate(dataSource);\n\nredisTemplate.setOptions(new Options());\nredisTemplate.afterPropertiesSet();\n\n// 将 User 对象写进 key 为 user hash 中\nredisTemplate.hSet(\"user\", \"1\", new User());\n\n// 获取 key 为 user ，field 为 1 的 hash 中的数据，并转换为 User\nUser user = redisTemplate.hGetObject(\"user\", \"1\", User.class);\n\n// 获取 key 为 user 的 hash 的所有数据，并将值转换为 User\nMap data = redisTemplate.hGetAllObject(\"user\", \"1\", new TypeReference>{});\n"
        },
        {
          "title": "展望",
          "url": "/manual/1.0/spring/index.html#展望",
          "content": "展望目前，buession-redis 仅支持 jedis，不支持 lettuce，我们预计会在下个版本或者下下个版本（即：2.1 或者 2.2）中计划加入。其实，之前尝试过，但由于两者 API 差异性和使用方式太大，没法很好的做到统一化，就暂时放弃了。"
        },
        {
          "title": "API 参考手册>>",
          "url": "/manual/1.0/spring/index.html#api-参考手册>>",
          "content": "API 参考手册>>"
        }
      ]
    },
    {
      "title": "buession-session 参考手册",
      "content": "无文档",
      "url": "/manual/1.0/springboot/index.html",
      "children": [
        {
          "title": "安装",
          "url": "/manual/1.0/springboot/index.html#安装",
          "content": "安装    com.buession\n    buession-session\n    x.x.x\n\n该模块无实际意义，将在今后的版本中会删除掉。"
        },
        {
          "title": "API 参考手册>>",
          "url": "/manual/1.0/springboot/index.html#api-参考手册>>",
          "content": "API 参考手册>>"
        }
      ]
    }
  ]
}