INSERT INTO feed (feed_id,category,title,user_id,view_count,pick_count,todo_count,todo_schedule_day,image)
VALUES (1,'CAREER','피드1 제목입니다.',1,0,0,0,0,'http://server/feeds/images/1.jpg');

INSERT INTO feed_content (content_id,title,content,order_number,feed_id)
VALUES (1,'1.소제목','1.내용',1,1);

INSERT INTO feed_mindset (mindset_id, content, feed_id)
VALUES (1, '피드1의 마인드셋1', 1);

INSERT INTO feed_mindset (mindset_id, content, feed_id)
VALUES (2, '피드1의 마인드셋2', 1);

INSERT INTO feed_todo (todo_id, type, title, depth, order_number, period, repetition_type, repetition_params, notification, parent_todo_id, feed_id)
VALUES (1,'TASK','투두타스크1뎁스',1,1,90,'DAY',null,'0900',null,1);

INSERT INTO feed_todo (todo_id, type, title, depth, order_number, period, repetition_type, repetition_params, notification, parent_todo_id, feed_id)
VALUES (2,'FOLDER','투두폴더1뎁스',1,2,90,null,null,null,null,1);
INSERT INTO feed_todo (todo_id, type, title, depth, order_number, period, repetition_type, repetition_params, notification, parent_todo_id, feed_id)
VALUES (3,'TASK','투두폴더1뎁스_타스크1',2,1,90,'DAY',null,'0900',2,1);

INSERT INTO feed (feed_id,category,title,user_id,view_count,pick_count,todo_count,todo_schedule_day,image)
VALUES (2,'CAREER','피드2 제목입니다.',1,0,0,0,0,'http://server/feeds/images/2.jpg');