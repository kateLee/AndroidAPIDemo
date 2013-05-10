/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tool.androidapidemo.app.listviewtutorial;

/**
 * Helper class providing methods and constants common to other classes in the
 * app.
 */
public final class CommonUtilities {

    /**
     * Base URL of the JSON data
     */
    static final String SERVER_URL = null;
    static final String DATA_URL = null;
    /**
     * fake JSON data
     */
    static final String JSON_DATA = "{\"meta\": {\"limit\": 5, \"next\": \"/nextUrl/\", \"offset\": 0, \"previous\": null, \"total_count\": 8503}, \"objects\": [{\"author_image_url\": \"https://graph.facebook.com/420217918039094/picture\", \"author_name\": \"靜香和大雄的交換日記\", \"author_url\": \"https://facebook.com/420217918039094\", \"comments_count\": 1, \"created_time\": \"2013-05-10T15:18:38+08:00\", \"id\": \"420217918039094_516247041769514\", \"image_url\": \"http://photos-b.xx.fbcdn.net/hphotos-prn1/s640x640/934060_516247021769516_1028088584_n.jpg\", \"likes_count\": 336, \"message\": \"當女人常有一個毛病，就是喜歡逞強。\\r\\n\\r\\n靜香*\\r\\n\\r\\n\\r\\n\\r\\n\\r\\n\\r\\n\\r\\n\\r\\n\\r\\n大雄:而當男人也會長出現一種毛病，就是把女人的逞強當真。\", \"permalink\": \"http://www.facebook.com/photo.php?fbid=516247021769516&set=a.420940614633491.98243.420217918039094&type=1&relevant_count=1\", \"resource_uri\": \"/api/v1/reader/content/420217918039094_516247041769514\", \"shares_count\": 16, \"type\": \"photo\"}, {\"author_image_url\": \"https://graph.facebook.com/252347658118458/picture\", \"author_name\": \"櫻桃小丸子驚點語錄\", \"author_url\": \"https://facebook.com/252347658118458\", \"comments_count\": 23, \"created_time\": \"2013-05-10T15:00:01+08:00\", \"id\": \"252347658118458_596919523661268\", \"image_url\": \"http://photos-a.ak.fbcdn.net/hphotos-ak-snc6/s640x640/247544_596869950332892_1537977227_n.jpg\", \"likes_count\": 6462, \"message\": \"生活中，一半的麻煩是由於你說Yes 太快，說No 太慢！\", \"permalink\": \"http://www.facebook.com/photo.php?fbid=596869950332892&set=a.252395524780338.66907.252347658118458&type=1&relevant_count=1\", \"resource_uri\": \"/api/v1/reader/content/252347658118458_596919523661268\", \"shares_count\": 471, \"type\": \"photo\"}, {\"author_image_url\": \"https://graph.facebook.com/480233918708202/picture\", \"author_name\": \"無嘴貓想說話\", \"author_url\": \"https://facebook.com/480233918708202\", \"comments_count\": 1, \"created_time\": \"2013-05-10T15:00:01+08:00\", \"id\": \"480233918708202_506469066084687\", \"image_url\": \"http://photos-d.ak.fbcdn.net/hphotos-ak-ash3/935445_506182979446629_755938185_s.png\", \"likes_count\": 623, \"message\": \"人，不是你對他好，他就會對你好，這也是沒辦法之事。\\r\\n \\r\\n 對人要和善，卻要有所保留，因為真心是留給值得對待之人。\", \"permalink\": \"http://www.facebook.com/photo.php?fbid=506182979446629&set=a.486242188107375.1073741828.480233918708202&type=1&relevant_count=1\", \"resource_uri\": \"/api/v1/reader/content/480233918708202_506469066084687\", \"shares_count\": 106, \"type\": \"photo\"}, {\"author_image_url\": \"https://graph.facebook.com/257113731021454/picture\", \"author_name\": \"宮崎駿的夢想之城\", \"author_url\": \"https://facebook.com/257113731021454\", \"comments_count\": 18, \"created_time\": \"2013-05-10T13:00:00+08:00\", \"id\": \"257113731021454_501454413254050\", \"image_url\": \"http://photos-g.ak.fbcdn.net/hphotos-ak-ash3/s640x640/943358_501432579922900_1837310794_n.jpg\", \"likes_count\": 6513, \"message\": \"不要把別人想像得對你很忠誠\", \"permalink\": \"http://www.facebook.com/photo.php?fbid=501432579922900&set=a.381590625240430.85967.257113731021454&type=1&relevant_count=1\", \"resource_uri\": \"/api/v1/reader/content/257113731021454_501454413254050\", \"shares_count\": 336, \"type\": \"photo\"}, {\"author_image_url\": \"https://graph.facebook.com/224468120947363/picture\", \"author_name\": \"海賊王驚點語錄\", \"author_url\": \"https://facebook.com/224468120947363\", \"comments_count\": 51, \"created_time\": \"2013-05-10T12:33:21+08:00\", \"id\": \"224468120947363_526298124097693\", \"image_url\": \"http://photos-a.ak.fbcdn.net/hphotos-ak-ash4/s640x640/485444_526298110764361_1095540502_n.jpg\", \"likes_count\": 6059, \"message\": \"初吻不是天長地久的愛情保障，但卻是蓋在心中的永恆印章。\", \"permalink\": \"http://www.facebook.com/photo.php?fbid=526298110764361&set=a.224471947613647.56645.224468120947363&type=1&relevant_count=1\", \"resource_uri\": \"/api/v1/reader/content/224468120947363_526298124097693\", \"shares_count\": 241, \"type\": \"photo\"}]}"; 
    /**
     * ID of your Facebook application
    */
    static final String APP_ID = null;
}
