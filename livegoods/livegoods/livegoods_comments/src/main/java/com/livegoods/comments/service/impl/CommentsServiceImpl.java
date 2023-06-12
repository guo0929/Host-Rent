package com.livegoods.comments.service.impl;

import com.livegoods.comments.dao.CommentsDao;
import com.livegoods.comments.service.CommentsService;
import com.livegoods.commons.pojo.Result;
import com.livegoods.pojo.Comments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据脱敏，可以在多个位置实现。
 * 1、 数据库端。 可选。如果敏感数据，永远不会作为任何查询条件出现，可以使用。其他情况不推荐使用。
 * 2、 后端代码逻辑。 推荐，通过代码实现脱敏，可以保证原数据可靠，脱敏数据自定义。
 * 3、 前端显示逻辑。 不推荐。因为名为已经通过网络传递，容易丢失敏感数据。
 */

@Service
public class CommentsServiceImpl implements CommentsService {
    @Autowired
    private CommentsDao commentsDao;
    @Value("${livegoods.comments.defaultRows}")
    private int defaultRows;

    @Override
    public Result<Comments> getCommentsByHouseId(String id, int page) {
        // 分页查询评价
        List<Comments> list = commentsDao.findCommentsByHouseId(id, page, defaultRows);
        // 数据脱敏， 摆脱敏感。 脱离敏感。 把敏感数据，变形为可现实的数据。如：密码的*******，手机号的135****1234
        for(Comments comments: list){
            String username = comments.getUsername();
            // 字符串不可变，replaceAll，是根据第一个参数的正则，用第二个参数的字符做替换。
            // 结果新字符串，是通过方法返回值返回的。原数据，不变。
            username = username.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
            comments.setUsername(username);
        }

        // 查询总计数据行数
        long totalRows = commentsDao.findCommentsRowsByHouseId(id);
        // 总计页数
        long pages = (totalRows % defaultRows == 0) ? (totalRows / defaultRows) : ((totalRows / defaultRows) + 1);
        // 处理结果
        Result<Comments> result = new Result<>();
        result.setStatus(200);
        result.setHasMore(pages - page > 1);
        result.setResults(list);

        return result;
    }
}
