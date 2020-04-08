package com.mini.studyservice.core.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UploadFileResponse {
    private Long id;
    private String storedName;
    private String fileDownloadUri;
    private long size;
}
