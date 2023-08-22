package it.unisalento.pas.wastedisposalagencybe.services;

import it.unisalento.pas.wastedisposalagencybe.domains.User;

public interface IUserService {

    int createUser(User user);
    int updateUser(User user);
    int deleteUser(String ID);
    int existUser(String email);
    User findByID(String ID);
}
