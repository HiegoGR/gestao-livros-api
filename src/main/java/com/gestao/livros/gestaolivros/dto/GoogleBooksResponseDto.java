package com.gestao.livros.gestaolivros.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoogleBooksResponseDto {
    private String kind;
    private Integer totalItems;
    private List<ItemDto> items;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemDto {
        private String kind;
        private String id;
        private String etag;
        private String selfLink;
        private VolumeInfoDto volumeInfo;
        private SaleInfoDto saleInfo;
        private AccessInfoDto accessInfo;
        private SearchInfoDto searchInfo;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VolumeInfoDto {
        private String title;
        private String subtitle;
        private List<String> authors;
        private String publisher;
        private String publishedDate;
        private String description;
        private List<IndustryIdentifierDto> industryIdentifiers;
        private List<CategoriesDto> categories;
        private ReadingModesDto readingModes;
        private Integer pageCount;
        private String printType;
        private String maturityRating;
        private Boolean allowAnonLogging;
        private String contentVersion;
        private ImageLinksDto imageLinks;
        private String language;
        private String previewLink;
        private String infoLink;
        private String canonicalVolumeLink;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IndustryIdentifierDto {
        private String type;
        private String identifier;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoriesDto {
        private String identifierCategory;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReadingModesDto {
        private Boolean text;
        private Boolean image;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImageLinksDto {
        private String smallThumbnail;
        private String thumbnail;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SaleInfoDto {
        private String country;
        private String saleability;
        private Boolean isEbook;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccessInfoDto {
        private String country;
        private String viewability;
        private Boolean embeddable;
        private Boolean publicDomain;
        private String textToSpeechPermission;
        private EpubDto epub;
        private PdfDto pdf;
        private String webReaderLink;
        private String accessViewStatus;
        private Boolean quoteSharingAllowed;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EpubDto {
        private Boolean isAvailable;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PdfDto {
        private Boolean isAvailable;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchInfoDto {
        private String textSnippet;
    }

}
