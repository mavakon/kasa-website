package com.bdsk.kasa.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdsk.kasa.domain.ConfirmedOrder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@ExtendWith(MockitoExtension.class)
public class ConfirmedOrderRepositoryTest {

}