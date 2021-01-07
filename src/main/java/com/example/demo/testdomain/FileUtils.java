package com.example.demo.testdomain;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

//https://www.jb51.net/article/198932.htm
@Slf4j
public class FileUtils
{
    /**
     * @Description: 读取resources 目录下的文件
     * @Author: ljj
     * @CreateDate: 2020/11/3 17:20
     * @UpdateUser:
     * @UpdateDate:
     * @UpdateReakem
     * @param filePath
     * @Return: java.lang.String
     **/

    public static String getContent(String filePath)
    {
        String res = "";
        if (StringUtils.isEmpty(filePath))
        {
            log.info("文件路径不能为空");
            return res;
        }
        try
        {
            Resource resource = new ClassPathResource(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream(), "UTF-8"));
            StringBuffer sb = new StringBuffer();
            String str = "";
            while ((str = br.readLine()) != null)
            {
                sb.append(str);
            }
            res = sb.toString();
        }
        catch (Exception e)
        {
            log.info("读取文件{}时发生异常", filePath);
            e.printStackTrace();
        }
        return res;
    }

    public static void main(String[] args)
    {
        List<TesDomainObj> readJsonGetData = readJsonGetData("checkDomain.json");

        for (TesDomainObj tesDomainObj : readJsonGetData)
        {
            if (null == tesDomainObj)
            {
                continue;
            }

            String hostname = tesDomainObj.getDomain(); // hostname 可以是主机的 IP 或者 域名
            int port = tesDomainObj.getPort();

            int timeout = 200;
            boolean isConnected = TelnetUtil.telnet(hostname, port, timeout);

            log.info("isConnected={},hostname={} ,port={} ", isConnected, hostname, port);
        }

    }

    public static List<TesDomainObj> readJsonGetData(String path)
    {
        List<TesDomainObj> respList = new ArrayList<>();

        String Content = FileUtils.getContent(path);
        log.info(Content);
        List<TesDomainObj> students = JSON.parseArray(Content, TesDomainObj.class);

        for (TesDomainObj tesDomainObj : students)
        {
            if (null == tesDomainObj.getDomain() || "".equals(tesDomainObj.getDomain())
                || null == tesDomainObj.getPort())
            {
                continue;
            }
            // log.info(tesDomainObj.toString());

            respList.add(tesDomainObj);
        }

        for (TesDomainObj tesDomainObj : respList)
        {
            log.info(tesDomainObj.toString());
        }

        return respList;
    }
}
