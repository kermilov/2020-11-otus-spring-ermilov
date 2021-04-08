package ru.otus.spring.kermilov.books.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.otus.spring.kermilov.books.dao.UserDao;
import ru.otus.spring.kermilov.books.domain.User;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.getByName(username).orElseThrow(
                () -> new UsernameNotFoundException("UsernameNotFoundException for username = " + username));
        return org.springframework.security.core.userdetails.User.withUsername(user.getName())
                .password(user.getPassword()).roles(user.getRoles().split(",")).build();
    }

}
