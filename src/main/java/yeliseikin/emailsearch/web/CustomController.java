package yeliseikin.emailsearch.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "CustomController")
@RestController
@RequestMapping("/custom")
@Tag(name="get custom list", description = "get custom list")
public class CustomController {

    @ApiOperation(value = "Get test1 ", response = String.class, tags = "/custom/test1")
    @GetMapping("/test1")
    public String parseurl() {
        return "test 1";
    }

}
