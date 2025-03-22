package com.project.medicalappointment.common.model;

import java.util.List;

/**
 * Global object which handles paginated format
 * @param page the page to get
 * @param pageSize the number of element of a page
 * @param content the list of element
 * @param <T> the type of element
 */
public record Page<T>(int page, int pageSize, List<T> content) {
}
