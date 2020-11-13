package top.zhouy.util.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.zhouy.commonresponse.bean.model.R;
import top.zhouy.util.utils.OssUtils;

import java.io.IOException;
import java.util.UUID;

/**
 * @description: ossController
 * @author: zhouy
 * @create: 2020-11-12 18:34:00
 */
@RestController
@RequestMapping("/oss")
@Api(description = "文件上传")
public class OssController {

    /**
     * 上传图片到OSS
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/uploadFile",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("上传文件到OSS")
    public R uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        JSONObject result = new JSONObject();
        //文件名
        String key= UUID.randomUUID().toString();
        String name = file.getOriginalFilename();
        String suffix = "";
        int index = name.lastIndexOf(".");
        if (index > 0){
            suffix = name.substring(index);
        }
        key = "blogAvatar/" + key;
        String strBackUrl = OssUtils.uploadFile(file.getInputStream(),key + suffix);
        result.put("id", key + suffix);
        result.put("url", strBackUrl);
        return R.okData(result);
    }

}
