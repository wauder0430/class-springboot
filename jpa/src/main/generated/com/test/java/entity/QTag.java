package com.test.java.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTag is a Querydsl query type for Tag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTag extends EntityPathBase<Tag> {

    private static final long serialVersionUID = -1274635476L;

    public static final QTag tag1 = new QTag("tag1");

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public final StringPath tag = createString("tag");

    public final ListPath<Tagging, QTagging> tagging = this.<Tagging, QTagging>createList("tagging", Tagging.class, QTagging.class, PathInits.DIRECT2);

    public QTag(String variable) {
        super(Tag.class, forVariable(variable));
    }

    public QTag(Path<? extends Tag> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTag(PathMetadata metadata) {
        super(Tag.class, metadata);
    }

}

