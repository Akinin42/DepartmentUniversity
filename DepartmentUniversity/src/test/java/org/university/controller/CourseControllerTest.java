package org.university.controller;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.university.dto.CourseDto;
import org.university.entity.Course;
import org.university.exceptions.InvalidCourseNameException;
import org.university.exceptions.InvalidDescriptionException;
import org.university.service.CourseService;
import org.university.utils.CreatorTestEntities;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class CourseControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CourseService courseServiceMock;

    private CourseController courseController;

    @BeforeEach
    public void setUpBeforeClass() throws Exception {
        courseController = new CourseController(courseServiceMock);
        mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
    }

    @Test
    void testGetAll() throws Exception {
        List<Course> courses = CreatorTestEntities.createCourses();
        when(courseServiceMock.findAllCourses()).thenReturn(courses);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/courses/");
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("courses"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("courses"))
                .andExpect(MockMvcResultMatchers.model().attribute("courses", courses));
    }

    @Test
    void testAdd() throws Exception {
        CourseDto course = new CourseDto();
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/courses/").flashAttr("course", course);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("redirect:/courses"));
        verify(courseServiceMock).addCourse(course);
    }

    @Test
    void testAddWhenInputInvalidName() throws Exception {
        CourseDto course = new CourseDto();
        course.setName("");
        doThrow(new InvalidCourseNameException("Input course name isn't valid!")).when(courseServiceMock)
                .addCourse(course);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/courses/").flashAttr("course", course);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("redirect:/courses"))
                .andExpect(MockMvcResultMatchers.model().attribute("message", "Input course name isn't valid!"));
    }

    @Test
    void testAddWhenInputInvalidDescription() throws Exception {
        CourseDto course = new CourseDto();
        course.setDescription("");
        doThrow(new InvalidDescriptionException("Input course description isn't valid!")).when(courseServiceMock)
                .addCourse(course);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/courses/").flashAttr("course", course);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("redirect:/courses"))
                .andExpect(MockMvcResultMatchers.model().attribute("message", "Input course description isn't valid!"));
    }

    @Test
    void testDelete() throws Exception {
        CourseDto course = new CourseDto();
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/courses/").flashAttr("course", course);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(MockMvcResultMatchers.view().name("redirect:/courses"));
        verify(courseServiceMock).delete(course);
    }
}
