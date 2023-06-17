package com.example.homeAssessment.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.example.homeAssessment.model.SatScore;
import com.example.homeAssessment.service.SatScoreService;


@Controller
public class SatScoreController {
    @Autowired
    private SatScoreService satScoreService;

    // Display main menu
    @GetMapping("/")
    public String viewHomePage(Model model) {
        return "index";
    }

    // Display form to insert records
    @GetMapping("/InsertSatScoreRecord")
    public String InsertSatScoreRecord(Model model) {
        // Create model attribute to bind form data
        SatScore satScore = new SatScore();
        model.addAttribute("satScore", satScore);
        return "new_satScore_record";
    }
    // show records in json format
    @GetMapping("/showRecords")
    public String viewRecords(Model model) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        //Set pretty printing of json
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        List < SatScore > satScore = satScoreService.getAllSatScores();
        // Converting the list of SatScore to array of Json
        String arrayToJson = objectMapper.writeValueAsString(satScore);

        model.addAttribute("listSatScores", arrayToJson);
        return "show_records";
    }
    // show records to update
    @GetMapping("/showRecordsToUpdate")
    public String viewRecordsToUpdate(Model model) {
        // Set all SAT Score data as model object
        model.addAttribute("listSatScores", satScoreService.getAllSatScores());
        return "show_records_to_update";
    }

    // show records to delete
    @GetMapping("/showRecordsToDelete")
    public String viewRecordsToDelete(Model model) {
        // Set all SAT Score data as model object
        model.addAttribute("listSatScores", satScoreService.getAllSatScores());
        return "show_records_to_delete";
    }
    // enter name to get Rank
    @GetMapping("/getName")
    public String getName(Model model) {
        return "get_name";
    }
    // show rank for given name
    @PostMapping("/viewRank")
    public String viewRank(@RequestParam("Name") String name, Model model,RedirectAttributes redirectAttributes) {
        // Checks whether the given name is exists or not else print error message
        Boolean exists = satScoreService.getExistsByName(name);

        if (exists != true){
            redirectAttributes.addFlashAttribute("errorMessage", "Name doesn't exist!");
            return "redirect:/getName";
        } else{
            // Calls getFindByRank method to find the rank for the given name and set as model attribute
            int r = satScoreService.getFindByRank(name);
            model.addAttribute("rank", r);
            return "view_rank";
        }
    }
    // Save the data
    @PostMapping("/saveSatScore")
    public String saveSatScore(@ModelAttribute("satScore") SatScore satScore, RedirectAttributes redirectAttributes) {
        // save SAT Score to database
        String name = satScore.getName();
        Boolean exists = satScoreService.getExistsByName(name);
        Integer score = satScore.getSatScore();
        // checks wether the SAT Score is in given range else print the error
        if (score < 400 || score > 1600){
            redirectAttributes.addFlashAttribute("errorforscore", "SAT Score is invalid!");
            return "redirect:/InsertSatScoreRecord";
        }else{
        // checks wether the given name is unique or not else print the error
        if (exists != false){
            redirectAttributes.addFlashAttribute("errorMessage", "Name is not unique!");
            return "redirect:/InsertSatScoreRecord";
        } else{
            
            //checks wether the SAT score is above 30%
            if(score >= 480) {
                satScore.setSatScore("Pass");
            } else {
                satScore.setSatScore("Fail");
            }
            satScoreService.saveSatScore(satScore);
            return "redirect:/";
        }
    }
    }
    // Update the SAT Score by a name
    @PostMapping("/updateSatScore")
    public String updateSatScore(@ModelAttribute("satScore") SatScore satScore,RedirectAttributes redirectAttributes) {
        // save SAT Score to database
        Integer score = satScore.getSatScore();
        if (score < 400 || score > 1600){
            redirectAttributes.addFlashAttribute("errorforscore", "SAT Score is invalid!");
            return "redirect:/showSatScoreRecordForUpdate/"+satScore.getName();
        }else{
        if(score >= 480) {
            satScore.setSatScore("Pass");
        } else {
            satScore.setSatScore("Fail");
        }
        satScoreService.saveSatScore(satScore);
        return "redirect:/showRecordsToUpdate";
    }
    }

    // Show SAT Score by a name to update
    @GetMapping("/showSatScoreRecordForUpdate/{name}")
    public String showSatScoreRecordForUpdate(@PathVariable(value = "name") String name, Model model) {

        SatScore satScore = satScoreService.getSatScoreByName(name);
        model.addAttribute("satScore", satScore);
        return "update_satScore";
    }

    // Delete the record
    @GetMapping("/deleteSatScore/{id}")
    public String deleteSatScore(@PathVariable(value = "id") long id) {
        this.satScoreService.deleteSatScoreById(id);
        return "redirect:/showRecordsToDelete";
    }
    
}
