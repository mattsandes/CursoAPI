package br.com.sandes.unittests.mapper;

import br.com.sandes.data.vo.v1.PersonVO;
import br.com.sandes.mapper.ModelMapper;
import br.com.sandes.model.Person;
import br.com.sandes.unittests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DozerConverterTests {

    MockPerson inputObject;

    @BeforeEach
    public void setUp(){
        inputObject = new MockPerson();
    }

    @Test
    public void parseEntityToVOTest(){
        PersonVO output = ModelMapper.parseObject(inputObject.mockEntity(), PersonVO.class);
        assertEquals(Long.valueOf(0L), output.getKey());
        assertEquals("First Name Test0", output.getFirst_name());
        assertEquals("Last Name Test0", output.getLast_name());
        assertEquals("Addres Test0", output.getAddress());
        assertEquals("Male", output.getGender());
    }

    @Test
    public void parseEntityListToVOList(){
        List<PersonVO> outPutList = ModelMapper.parseListObjects(inputObject.mockEntityList(), PersonVO.class);
        PersonVO outPutZero = outPutList.getFirst();

        assertEquals(Long.valueOf(0L), outPutZero.getKey());
        assertEquals("First Name Test0", outPutZero.getFirst_name());
        assertEquals("Last Name Test0", outPutZero.getLast_name());
        assertEquals("Addres Test0", outPutZero.getAddress());
        assertEquals("Male", outPutZero.getGender());
    }

    @Test
    public void parseEntityListToVOListTest() {
        List<PersonVO> outputList = ModelMapper.parseListObjects(inputObject.mockEntityList(), PersonVO.class);
        PersonVO outputZero = outputList.get(0);

        assertEquals(Long.valueOf(0L), outputZero.getKey());
        assertEquals("First Name Test0", outputZero.getFirst_name());
        assertEquals("Last Name Test0", outputZero.getLast_name());
        assertEquals("Addres Test0", outputZero.getAddress());
        assertEquals("Male", outputZero.getGender());

        PersonVO outputSeven = outputList.get(7);

        assertEquals(Long.valueOf(7L), outputSeven.getKey());
        assertEquals("First Name Test7", outputSeven.getFirst_name());
        assertEquals("Last Name Test7", outputSeven.getLast_name());
        assertEquals("Addres Test7", outputSeven.getAddress());
        assertEquals("Female", outputSeven.getGender());

        PersonVO outputTwelve = outputList.get(12);

        assertEquals(Long.valueOf(12L), outputTwelve.getKey());
        assertEquals("First Name Test12", outputTwelve.getFirst_name());
        assertEquals("Last Name Test12", outputTwelve.getLast_name());
        assertEquals("Addres Test12", outputTwelve.getAddress());
        assertEquals("Male", outputTwelve.getGender());
    }

    @Test
    public void parseVOToEntityTest() {
        Person output = ModelMapper.parseObject(inputObject.mockVO(), Person.class);
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("First Name Test0", output.getFirst_name());
        assertEquals("Last Name Test0", output.getLast_name());
        assertEquals("Addres Test0", output.getAddress());
        assertEquals("Male", output.getGender());
    }

    @Test
    public void parserVOListToEntityListTest() {
        List<Person> outputList = ModelMapper.parseListObjects(inputObject.mockVOList(), Person.class);
        Person outputZero = outputList.getFirst();

        assertEquals(Long.valueOf(0L), outputZero.getId());
        assertEquals("First Name Test0", outputZero.getFirst_name());
        assertEquals("Last Name Test0", outputZero.getLast_name());
        assertEquals("Addres Test0", outputZero.getAddress());
        assertEquals("Male", outputZero.getGender());

        Person outputSeven = outputList.get(7);

        assertEquals(Long.valueOf(7L), outputSeven.getId());
        assertEquals("First Name Test7", outputSeven.getFirst_name());
        assertEquals("Last Name Test7", outputSeven.getLast_name());
        assertEquals("Addres Test7", outputSeven.getAddress());
        assertEquals("Female", outputSeven.getGender());

        Person outputTwelve = outputList.get(12);

        assertEquals(Long.valueOf(12L), outputTwelve.getId());
        assertEquals("First Name Test12", outputTwelve.getFirst_name());
        assertEquals("Last Name Test12", outputTwelve.getLast_name());
        assertEquals("Addres Test12", outputTwelve.getAddress());
        assertEquals("Male", outputTwelve.getGender());
    }

}
