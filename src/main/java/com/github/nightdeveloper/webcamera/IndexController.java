package com.github.nightdeveloper.webcamera;

import com.github.nightdeveloper.webcamera.soap.MoveResult;
import com.github.nightdeveloper.webcamera.soap.PTZService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@EnableAutoConfiguration
public class IndexController {

    @Autowired
    PTZService ptzService;

    @RequestMapping("/move")
    public @ResponseBody MoveResult doMove(String direction, Long duration) {

        MoveResult result = new MoveResult();
        try {
            ptzService.move(direction, duration);

            result.setImageURL(ptzService.getSnapshotUri());
            result.setSuccess(true);
            result.setMessage("ok");
        } catch (Exception e) {
            result.setMessage(e.getMessage());
        }

        return result;
    }

    @RequestMapping("/")
    @ResponseBody
    public ModelAndView index() throws Exception {
        return new ModelAndView("index");
    }
}