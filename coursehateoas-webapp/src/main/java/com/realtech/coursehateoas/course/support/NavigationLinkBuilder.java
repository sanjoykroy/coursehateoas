package com.realtech.coursehateoas.course.support;


import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.LinkBuilderSupport;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

public class NavigationLinkBuilder extends LinkBuilderSupport<NavigationLinkBuilder> {

    public NavigationLinkBuilder(UriComponentsBuilder builder) {
        super(builder);
    }

    @Override
    protected NavigationLinkBuilder getThis() {
        return this;
    }

    @Override
    protected NavigationLinkBuilder createNewInstance(UriComponentsBuilder uriComponentsBuilder) {
        return new NavigationLinkBuilder(uriComponentsBuilder);
    }

    public static NavigationLinkBuilder linkToCurrentRequest(){
       return new NavigationLinkBuilder(ServletUriComponentsBuilder.fromCurrentRequest());
    }

    public static NavigationLinkBuilder linkToCurrentRequest(String pageName, String pageSizeName, int page, int pageSize){
        return new NavigationLinkBuilder(ServletUriComponentsBuilder.fromCurrentRequest()
        .replaceQueryParam(pageName, page)
        .replaceQueryParam(pageSizeName, pageSize));
    }

    public static void addNavigationLinks(ResourceSupport resource, Page page){

        String pageName = "page";
        String pageSizeName = "page_size";

        resource.add(linkToCurrentRequest().withSelfRel());

        if(!page.isFirstPage()){
            resource.add(linkToCurrentRequest(pageName, pageSizeName, 0, page.getSize()).withRel(Link.REL_FIRST));
        }

        if(!page.isLastPage() && page.hasContent()){
            resource.add(linkToCurrentRequest(pageName, pageSizeName, page.getTotalPages() - 1, page.getSize()).withRel(Link.REL_LAST));
        }

        if(page.hasPreviousPage()){
            resource.add(linkToCurrentRequest(pageName, pageSizeName, page.getNumber() - 1, page.getSize()).withRel(Link.REL_PREVIOUS));
        }

        if(page.hasNextPage()){
            resource.add(linkToCurrentRequest(pageName, pageSizeName, page.getNumber() + 1, page.getSize()).withRel(Link.REL_NEXT));
        }

    }

}
