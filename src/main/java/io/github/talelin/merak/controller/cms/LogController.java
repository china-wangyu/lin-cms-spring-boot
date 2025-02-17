package io.github.talelin.merak.controller.cms;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.core.annotation.GroupMeta;
import io.github.talelin.merak.common.util.ResponseUtil;
import io.github.talelin.merak.model.LogDO;
import io.github.talelin.merak.vo.PageResponseVO;
import io.github.talelin.merak.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.Date;

/**
 * @author pedro@TaleLin
 */
@RestController
@RequestMapping("/cms/log")
@Validated
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping("")
    @GroupMeta(permission = "查询所有日志", module = "日志", mount = true)
    public PageResponseVO getLogs(
            @RequestParam(name = "start", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date start,
            @RequestParam(name = "end", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date end,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{count}") Long count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page}") Long page) {
        IPage<LogDO> iPage = logService.getLogPage(page, count, name, start, end);
        return ResponseUtil.generatePageResult(iPage.getTotal(), iPage.getRecords(), page, count);
    }

    @GetMapping("/search")
    @GroupMeta(permission = "搜索日志", module = "日志", mount = true)
    public PageResponseVO searchLogs(
            @RequestParam(name = "start", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date start,
            @RequestParam(name = "end", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date end,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{count}") Long count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page}") Long page) {
        IPage<LogDO> iPage = logService.searchLogPage(page, count, name, keyword, start, end);
        return ResponseUtil.generatePageResult(iPage.getTotal(), iPage.getRecords(), page, count);
    }

    @GetMapping("/users")
    @GroupMeta(permission = "查询日志记录的用户", module = "日志", mount = true)
    public PageResponseVO getUsers(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @Min(value = 1, message = "{count}") Long count,
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "{page}") Long page) {
        IPage<String> iPage = logService.getUserNamePage(page, count);
        return ResponseUtil.generatePageResult(iPage.getTotal(), iPage.getRecords(), page, count);
    }
}
