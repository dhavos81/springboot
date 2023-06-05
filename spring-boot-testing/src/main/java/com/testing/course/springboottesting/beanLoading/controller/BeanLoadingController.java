package com.testing.course.springboottesting.beanLoading.controller;


import com.testing.course.springboottesting.beanLoading.common.Coach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BeanLoadingController {

    private Coach mycoach1;
    private Coach mycoach2;
    private Coach mycoach3;

    @Autowired
    BeanLoadingController(@Qualifier("cricketCoach") Coach coach){
        mycoach1=coach;
    }

    @Autowired
    private void setMycoach2(@Qualifier("tennisCoach") Coach coach){
        mycoach2 = coach;
    }

    @Autowired
    private void setMycoach3( Coach coach){
        mycoach3 = coach;
    }

    @Autowired
    private @Qualifier("swimmingCoach") Coach mycoach4;


    @GetMapping("/dailyWorkout")
    public String method(){
        return
                        mycoach1.getDailyWorkout()+" "+
                        mycoach2.getDailyWorkout()+" "+
                        mycoach3.getDailyWorkout()+" "+
                        mycoach4.getDailyWorkout();
    }

}
