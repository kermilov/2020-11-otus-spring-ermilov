package ru.otus.spring.kermilov.books.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.otus.spring.kermilov.books.dao.BookCommentDao;
import ru.otus.spring.kermilov.books.domain.BookComment;

@Service
@RequiredArgsConstructor
public class BookCommentAclServiceImpl implements BookCommentAclService {
    private final BookCommentDao bookCommentDao;
    private final MutableAclService aclService;

    @Override
    public List<BookComment> getByBookId(long id) {
        return bookCommentDao.getByBookId(id);
//        var result = bookCommentDao.getByBookId(id);
//        var rights = isGranted(result.stream().map(BookComment::getId).collect(Collectors.toList()));
//        return result.stream().filter(f -> rights.get(f.getId())).collect(Collectors.toList());
    }

    @Override
    public Optional<BookComment> findById(Long id) {
        return bookCommentDao.findById(id);
    }

    @Override
    public BookComment save(BookComment entity) {
        long id = entity.getId();
        if (id > 0) {
            if (!isGranted(id)) {
                throw new RuntimeException("Access denied");
            }
        }
        BookComment save = bookCommentDao.save(entity);
        if (id == 0) {
            giveGrant(save.getId());
        }
        return save;
    }

    @Override
    public void deleteById(long id) {
        if (isGranted(id)) {
            bookCommentDao.deleteById(id);
        } else {
            throw new RuntimeException("Access denied");
        }
    }

    private Boolean isGranted(Long id) {
        ObjectIdentity oid = new ObjectIdentityImpl(BookComment.class, id);
        // прочитать ACL бизнес сущности
        Acl acl = aclService.readAclById(oid);

        // определить какие права и для кого проверять
        final List<Permission> permissions = Arrays.asList(BasePermission.ADMINISTRATION);
        final List<Sid> sids = Arrays.asList((Sid) new PrincipalSid(SecurityContextHolder.getContext().getAuthentication()));
        // выполнить проверку
        try {
            return acl.isGranted(permissions, sids, false);
        } catch (org.springframework.security.acls.model.NotFoundException e) {
            return false;
        }
    }

    private Map<Long, Boolean> isGranted(List<Long> idsList) {
        List<ObjectIdentity> oidsList = idsList.stream().map(id -> new ObjectIdentityImpl(BookComment.class, id)).collect(Collectors.toList());
        // прочитать ACL бизнес сущности
        Map<ObjectIdentity, Acl> acls = aclService.readAclsById(oidsList);
        // определить какие права и для кого проверять
        final List<Permission> permissions = Arrays.asList(BasePermission.ADMINISTRATION);
        final List<Sid> sids = Arrays.asList((Sid) new PrincipalSid(SecurityContextHolder.getContext().getAuthentication()));
        var result = new HashMap<Long, Boolean>();
        // выполнить проверку
        acls.forEach((oid, acl) -> {
            try {
                result.put((Long) oid.getIdentifier(), acl.isGranted(permissions, sids, false));
            } catch (org.springframework.security.acls.model.NotFoundException e) {
                result.put((Long) oid.getIdentifier(), false);
            }
        });
        return result;
    }

    private void giveGrant(long id) {
        ObjectIdentity oid = new ObjectIdentityImpl(BookComment.class, id);
        MutableAcl acl = aclService.createAcl(oid);
        Sid owner = new PrincipalSid(SecurityContextHolder.getContext().getAuthentication());
        acl.setOwner(owner);
        acl.insertAce(acl.getEntries().size(), BasePermission.ADMINISTRATION, owner, true);
        // обновить ACL в БД
        aclService.updateAcl(acl);
    }
}
