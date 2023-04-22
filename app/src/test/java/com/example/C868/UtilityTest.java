package com.example.C868;


import android.text.Editable;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class UtilityTest extends TestCase {


    @Test
    public void testIsValidName() {
        Editable mockEditable = mock(Editable.class);
        when(mockEditable.toString()).thenReturn("Albert");

        EditText mockEditText = mock(EditText.class);
        when(mockEditText.getText()).thenReturn(mockEditable);

        TextInputLayout mockNameField = mock(TextInputLayout.class);
        when(mockNameField.getEditText()).thenReturn(mockEditText);

        boolean result = Utility.isValidName(mockNameField);
        assertTrue(result);
    }

    @Test
    public void testIsValidNameWhitespace() {
        Editable mockEditable = mock(Editable.class);
        when(mockEditable.toString()).thenReturn("Albert Einstein");

        EditText mockEditText = mock(EditText.class);
        when(mockEditText.getText()).thenReturn(mockEditable);

        TextInputLayout mockNameField = mock(TextInputLayout.class);
        when(mockNameField.getEditText()).thenReturn(mockEditText);

        boolean result = Utility.isValidName(mockNameField);
        assertFalse(result);
    }

    @Test
    public void testIsValidNameDiacritic() {
        Editable mockEditable = mock(Editable.class);
        when(mockEditable.toString()).thenReturn("ЍAlbért");

        EditText mockEditText = mock(EditText.class);
        when(mockEditText.getText()).thenReturn(mockEditable);

        TextInputLayout mockNameField = mock(TextInputLayout.class);
        when(mockNameField.getEditText()).thenReturn(mockEditText);

        boolean result = Utility.isValidName(mockNameField);
        assertFalse(result);
    }

    @Test
    public void testIsValidNameEmpty() {
        Editable mockEditable = mock(Editable.class);
        when(mockEditable.toString()).thenReturn("");

        EditText mockEditText = mock(EditText.class);
        when(mockEditText.getText()).thenReturn(mockEditable);

        TextInputLayout mockNameField = mock(TextInputLayout.class);
        when(mockNameField.getEditText()).thenReturn(mockEditText);

        boolean result = Utility.isValidName(mockNameField);
        assertFalse(result);
    }

    @Test
    public void testIsValidNameOverTwelve() {
        Editable mockEditable = mock(Editable.class);
        when(mockEditable.toString()).thenReturn("abcdefghijklmnop");

        EditText mockEditText = mock(EditText.class);
        when(mockEditText.getText()).thenReturn(mockEditable);

        TextInputLayout mockNameField = mock(TextInputLayout.class);
        when(mockNameField.getEditText()).thenReturn(mockEditText);

        boolean result = Utility.isValidName(mockNameField);
        assertFalse(result);
    }

    @Test
    public void testIsValidNameUnderThree() {
        Editable mockEditable = mock(Editable.class);
        when(mockEditable.toString()).thenReturn("ab");

        EditText mockEditText = mock(EditText.class);
        when(mockEditText.getText()).thenReturn(mockEditable);

        TextInputLayout mockNameField = mock(TextInputLayout.class);
        when(mockNameField.getEditText()).thenReturn(mockEditText);

        boolean result = Utility.isValidName(mockNameField);
        assertFalse(result);
    }

    @Test
    public void testIsValidUsername() {
        Editable mockEditable = mock(Editable.class);
        when(mockEditable.toString()).thenReturn("ielkhat");

        EditText mockEditText = mock(EditText.class);
        when(mockEditText.getText()).thenReturn(mockEditable);

        TextInputLayout mockUsernameField = mock(TextInputLayout.class);
        when(mockUsernameField.getEditText()).thenReturn(mockEditText);

        boolean result = Utility.isValidUsername(mockUsernameField);
        assertTrue(result);
    }

    @Test
    public void testIsValidUsernameAlphaNumeric() {
        Editable mockEditable = mock(Editable.class);
        when(mockEditable.toString()).thenReturn("ielkhat123");

        EditText mockEditText = mock(EditText.class);
        when(mockEditText.getText()).thenReturn(mockEditable);

        TextInputLayout mockUsernameField = mock(TextInputLayout.class);
        when(mockUsernameField.getEditText()).thenReturn(mockEditText);

        boolean result = Utility.isValidUsername(mockUsernameField);
        assertTrue(result);
    }

    @Test
    public void testIsValidUsernameWhiteSpace() {
        Editable mockEditable = mock(Editable.class);
        when(mockEditable.toString()).thenReturn("iel khat");

        EditText mockEditText = mock(EditText.class);
        when(mockEditText.getText()).thenReturn(mockEditable);

        TextInputLayout mockUsernameField = mock(TextInputLayout.class);
        when(mockUsernameField.getEditText()).thenReturn(mockEditText);

        boolean result = Utility.isValidUsername(mockUsernameField);
        assertFalse(result);
    }

    @Test
    public void testIsValidUsernameDiacritic() {
        Editable mockEditable = mock(Editable.class);
        when(mockEditable.toString()).thenReturn("ielkhát");

        EditText mockEditText = mock(EditText.class);
        when(mockEditText.getText()).thenReturn(mockEditable);

        TextInputLayout mockUsernameField = mock(TextInputLayout.class);
        when(mockUsernameField.getEditText()).thenReturn(mockEditText);

        boolean result = Utility.isValidUsername(mockUsernameField);
        assertFalse(result);
    }

    @Test
    public void testIsValidUsernameEmpty() {
        Editable mockEditable = mock(Editable.class);
        when(mockEditable.toString()).thenReturn("");

        EditText mockEditText = mock(EditText.class);
        when(mockEditText.getText()).thenReturn(mockEditable);

        TextInputLayout mockUsernameField = mock(TextInputLayout.class);
        when(mockUsernameField.getEditText()).thenReturn(mockEditText);

        boolean result = Utility.isValidUsername(mockUsernameField);
        assertFalse(result);
    }

    @Test
    public void testIsValidUsernameOverTwelve() {
        Editable mockEditable = mock(Editable.class);
        when(mockEditable.toString()).thenReturn("ielkhat000000");

        EditText mockEditText = mock(EditText.class);
        when(mockEditText.getText()).thenReturn(mockEditable);

        TextInputLayout mockUsernameField = mock(TextInputLayout.class);
        when(mockUsernameField.getEditText()).thenReturn(mockEditText);

        boolean result = Utility.isValidUsername(mockUsernameField);
        assertFalse(result);
    }

    @Test
    public void testIsValidUsernameUnderFour() {
        Editable mockEditable = mock(Editable.class);
        when(mockEditable.toString()).thenReturn("iel");

        EditText mockEditText = mock(EditText.class);
        when(mockEditText.getText()).thenReturn(mockEditable);

        TextInputLayout mockUsernameField = mock(TextInputLayout.class);
        when(mockUsernameField.getEditText()).thenReturn(mockEditText);

        boolean result = Utility.isValidUsername(mockUsernameField);
        assertFalse(result);
    }

    @Test
    public void testIsValidPassword() {
        Editable mockEditable = mock(Editable.class);
        when(mockEditable.toString()).thenReturn("lollipop");

        EditText mockEditText = mock(EditText.class);
        when(mockEditText.getText()).thenReturn(mockEditable);

        TextInputLayout mockPasswordField = mock(TextInputLayout.class);
        when(mockPasswordField.getEditText()).thenReturn(mockEditText);

        boolean result = Utility.isValidPassword(mockPasswordField);
        assertTrue(result);
    }

    @Test
    public void testIsValidPasswordCharacterCombo() {
        Editable mockEditable = mock(Editable.class);
        when(mockEditable.toString()).thenReturn("l0Ll!p0p123");

        EditText mockEditText = mock(EditText.class);
        when(mockEditText.getText()).thenReturn(mockEditable);

        TextInputLayout mockPasswordField = mock(TextInputLayout.class);
        when(mockPasswordField.getEditText()).thenReturn(mockEditText);

        boolean result = Utility.isValidPassword(mockPasswordField);
        assertTrue(result);
    }

    @Test
    public void testIsValidPasswordWhiteSpace() {
        Editable mockEditable = mock(Editable.class);
        when(mockEditable.toString()).thenReturn("lolli pop");

        EditText mockEditText = mock(EditText.class);
        when(mockEditText.getText()).thenReturn(mockEditable);

        TextInputLayout mockPasswordField = mock(TextInputLayout.class);
        when(mockPasswordField.getEditText()).thenReturn(mockEditText);

        boolean result = Utility.isValidPassword(mockPasswordField);
        assertFalse(result);
    }

    @Test
    public void testIsValidPasswordDiacritic() {
        Editable mockEditable = mock(Editable.class);
        when(mockEditable.toString()).thenReturn("lóllipóp");

        EditText mockEditText = mock(EditText.class);
        when(mockEditText.getText()).thenReturn(mockEditable);

        TextInputLayout mockPasswordField = mock(TextInputLayout.class);
        when(mockPasswordField.getEditText()).thenReturn(mockEditText);

        boolean result = Utility.isValidPassword(mockPasswordField);
        assertTrue(result);
    }

    @Test
    public void testIsValidPasswordEmpty() {
        Editable mockEditable = mock(Editable.class);
        when(mockEditable.toString()).thenReturn("");

        EditText mockEditText = mock(EditText.class);
        when(mockEditText.getText()).thenReturn(mockEditable);

        TextInputLayout mockPasswordField = mock(TextInputLayout.class);
        when(mockPasswordField.getEditText()).thenReturn(mockEditText);

        boolean result = Utility.isValidPassword(mockPasswordField);
        assertFalse(result);
    }

    @Test
    public void testIsValidPasswordUnderFour() {
        Editable mockEditable = mock(Editable.class);
        when(mockEditable.toString()).thenReturn("pop");

        EditText mockEditText = mock(EditText.class);
        when(mockEditText.getText()).thenReturn(mockEditable);

        TextInputLayout mockPasswordField = mock(TextInputLayout.class);
        when(mockPasswordField.getEditText()).thenReturn(mockEditText);

        boolean result = Utility.isValidPassword(mockPasswordField);
        assertFalse(result);
    }

    @Test
    public void testIsMatchingPassword() {
        Editable mockEditable1 = mock(Editable.class);
        when(mockEditable1.toString()).thenReturn("lollipop");

        Editable mockEditable2 = mock(Editable.class);
        when(mockEditable2.toString()).thenReturn("lollipop");

        EditText mockEditText1 = mock(EditText.class);
        when(mockEditText1.getText()).thenReturn(mockEditable1);

        EditText mockEditText2 = mock(EditText.class);
        when(mockEditText2.getText()).thenReturn(mockEditable2);

        TextInputLayout mockPasswordField1 = mock(TextInputLayout.class);
        when(mockPasswordField1.getEditText()).thenReturn(mockEditText1);

        TextInputLayout mockPasswordField2 = mock(TextInputLayout.class);
        when(mockPasswordField2.getEditText()).thenReturn(mockEditText2);

        boolean result = Utility.isMatchingPassword(mockPasswordField1, mockPasswordField2);
        assertTrue(result);
    }

    @Test
    public void testIsNotMatchingPassword() {
        Editable mockEditable1 = mock(Editable.class);
        when(mockEditable1.toString()).thenReturn("lolli");

        Editable mockEditable2 = mock(Editable.class);
        when(mockEditable2.toString()).thenReturn("pop");

        EditText mockEditText1 = mock(EditText.class);
        when(mockEditText1.getText()).thenReturn(mockEditable1);

        EditText mockEditText2 = mock(EditText.class);
        when(mockEditText2.getText()).thenReturn(mockEditable2);

        TextInputLayout mockPasswordField1 = mock(TextInputLayout.class);
        when(mockPasswordField1.getEditText()).thenReturn(mockEditText1);

        TextInputLayout mockPasswordField2 = mock(TextInputLayout.class);
        when(mockPasswordField2.getEditText()).thenReturn(mockEditText2);

        boolean result = Utility.isMatchingPassword(mockPasswordField1, mockPasswordField2);
        assertFalse(result);
    }

    @Test
    public void testIsValidTitle() {
        Editable mockEditable = mock(Editable.class);
        when(mockEditable.toString()).thenReturn("C868");

        EditText mockEditText = mock(EditText.class);
        when(mockEditText.getText()).thenReturn(mockEditable);

        TextInputLayout mockTitleField = mock(TextInputLayout.class);
        when(mockTitleField.getEditText()).thenReturn(mockEditText);

        boolean result = Utility.isValidTitle(mockTitleField);
        assertTrue(result);
    }

    @Test
    public void testIsValidTitleDiacritic() {
        Editable mockEditable = mock(Editable.class);
        when(mockEditable.toString()).thenReturn("Térm");

        EditText mockEditText = mock(EditText.class);
        when(mockEditText.getText()).thenReturn(mockEditable);

        TextInputLayout mockTitleField = mock(TextInputLayout.class);
        when(mockTitleField.getEditText()).thenReturn(mockEditText);

        boolean result = Utility.isValidTitle(mockTitleField);
        assertTrue(result);
    }

    @Test
    public void testIsValidTitleOverTwelve() {
        Editable mockEditable = mock(Editable.class);
        when(mockEditable.toString()).thenReturn("1234567891011");

        EditText mockEditText = mock(EditText.class);
        when(mockEditText.getText()).thenReturn(mockEditable);

        TextInputLayout mockTitleField = mock(TextInputLayout.class);
        when(mockTitleField.getEditText()).thenReturn(mockEditText);

        boolean result = Utility.isValidTitle(mockTitleField);
        assertFalse(result);
    }

    @Test
    public void testIsValidTitleUnderThree() {
        Editable mockEditable = mock(Editable.class);
        when(mockEditable.toString()).thenReturn("12");

        EditText mockEditText = mock(EditText.class);
        when(mockEditText.getText()).thenReturn(mockEditable);

        TextInputLayout mockTitleField = mock(TextInputLayout.class);
        when(mockTitleField.getEditText()).thenReturn(mockEditText);

        boolean result = Utility.isValidTitle(mockTitleField);
        assertFalse(result);
    }

    @Test
    public void testIsValidSearchInput() {
        Editable mockEditable = mock(Editable.class);
        when(mockEditable.toString()).thenReturn("Course");

        EditText mockEditText = mock(EditText.class);
        when(mockEditText.getText()).thenReturn(mockEditable);

        TextInputLayout mockSearchField = mock(TextInputLayout.class);
        when(mockSearchField.getEditText()).thenReturn(mockEditText);

        boolean result = Utility.isValidSearchInput(mockSearchField);
        assertTrue(result);
    }

    @Test
    public void testIsNotValidSearchInput() {
        Editable mockEditable = mock(Editable.class);
        when(mockEditable.toString()).thenReturn("");

        EditText mockEditText = mock(EditText.class);
        when(mockEditText.getText()).thenReturn(mockEditable);

        TextInputLayout mockSearchField = mock(TextInputLayout.class);
        when(mockSearchField.getEditText()).thenReturn(mockEditText);

        boolean result = Utility.isValidSearchInput(mockSearchField);
        assertFalse(result);
    }

    @Test
    public void testIsValidPhone() {
        Editable mockEditable = mock(Editable.class);
        when(mockEditable.toString()).thenReturn("12345678910");

        EditText mockEditText = mock(EditText.class);
        when(mockEditText.getText()).thenReturn(mockEditable);

        TextInputLayout mockPhoneField = mock(TextInputLayout.class);
        when(mockPhoneField.getEditText()).thenReturn(mockEditText);

        boolean result = Utility.isValidPhone(mockPhoneField);
        assertTrue(result);
    }

    @Test
    public void testIsNotValidPhone() {
        Editable mockEditable = mock(Editable.class);
        when(mockEditable.toString()).thenReturn("12345a3424");

        EditText mockEditText = mock(EditText.class);
        when(mockEditText.getText()).thenReturn(mockEditable);

        TextInputLayout mockPhoneField = mock(TextInputLayout.class);
        when(mockPhoneField.getEditText()).thenReturn(mockEditText);

        boolean result = Utility.isValidPhone(mockPhoneField);
        assertFalse(result);
    }

    @Test
    public void testIValidPhoneOver15() {
        Editable mockEditable = mock(Editable.class);
        when(mockEditable.toString()).thenReturn("123456789101112131415");

        EditText mockEditText = mock(EditText.class);
        when(mockEditText.getText()).thenReturn(mockEditable);

        TextInputLayout mockPhoneField = mock(TextInputLayout.class);
        when(mockPhoneField.getEditText()).thenReturn(mockEditText);

        boolean result = Utility.isValidPhone(mockPhoneField);
        assertFalse(result);
    }

    @Test
    public void testIValidPhoneEmpty() {
        Editable mockEditable = mock(Editable.class);
        when(mockEditable.toString()).thenReturn("");

        EditText mockEditText = mock(EditText.class);
        when(mockEditText.getText()).thenReturn(mockEditable);

        TextInputLayout mockPhoneField = mock(TextInputLayout.class);
        when(mockPhoneField.getEditText()).thenReturn(mockEditText);

        boolean result = Utility.isValidPhone(mockPhoneField);
        assertFalse(result);
    }


    @Test
    public void testIsValidEmail() {
        Editable mockEditable = mock(Editable.class);
        when(mockEditable.toString()).thenReturn("ielkhat@wgu.edu");

        EditText mockEditText = mock(EditText.class);
        when(mockEditText.getText()).thenReturn(mockEditable);

        TextInputLayout mockEmailField = mock(TextInputLayout.class);
        when(mockEmailField.getEditText()).thenReturn(mockEditText);

        boolean result = Utility.isValidEmail(mockEmailField);
        assertTrue(result);
    }

    @Test
    public void testIsNotValidEmail() {
        Editable mockEditable = mock(Editable.class);
        when(mockEditable.toString()).thenReturn("ielkhat");

        EditText mockEditText = mock(EditText.class);
        when(mockEditText.getText()).thenReturn(mockEditable);

        TextInputLayout mockEmailField = mock(TextInputLayout.class);
        when(mockEmailField.getEditText()).thenReturn(mockEditText);

        boolean result = Utility.isValidEmail(mockEmailField);
        assertFalse(result);
    }

    @Test
    public void testIsNotValidEmail2() {
        Editable mockEditable = mock(Editable.class);
        when(mockEditable.toString()).thenReturn("ielkhat@wgu");

        EditText mockEditText = mock(EditText.class);
        when(mockEditText.getText()).thenReturn(mockEditable);

        TextInputLayout mockEmailField = mock(TextInputLayout.class);
        when(mockEmailField.getEditText()).thenReturn(mockEditText);

        boolean result = Utility.isValidEmail(mockEmailField);
        assertFalse(result);
    }

    @Test
    public void testIsNotValidEmail3() {
        Editable mockEditable = mock(Editable.class);
        when(mockEditable.toString()).thenReturn("wgu.edu");

        EditText mockEditText = mock(EditText.class);
        when(mockEditText.getText()).thenReturn(mockEditable);

        TextInputLayout mockEmailField = mock(TextInputLayout.class);
        when(mockEmailField.getEditText()).thenReturn(mockEditText);

        boolean result = Utility.isValidEmail(mockEmailField);
        assertFalse(result);
    }



    @Test
    public void testIsValidEmailEmpty() {
        Editable mockEditable = mock(Editable.class);
        when(mockEditable.toString()).thenReturn("");

        EditText mockEditText = mock(EditText.class);
        when(mockEditText.getText()).thenReturn(mockEditable);

        TextInputLayout mockEmailField = mock(TextInputLayout.class);
        when(mockEmailField.getEditText()).thenReturn(mockEditText);

        boolean result = Utility.isValidEmail(mockEmailField);
        assertFalse(result);
    }

    private String[] mockItems = {"Term 1", "Term 2", "Term 3"};

    @Test
    public void testIsValidDropDownSelection() {
        Editable mockEditable = mock(Editable.class);
        when(mockEditable.toString()).thenReturn("Term 1");

        EditText mockEditText = mock(EditText.class);
        when(mockEditText.getText()).thenReturn(mockEditable);

        TextInputLayout mockDropDownSelection = mock(TextInputLayout.class);
        when(mockDropDownSelection.getEditText()).thenReturn(mockEditText);

        boolean result = Utility.isValidDropDownSelection(mockDropDownSelection, mockItems);
        assertTrue(result);
    }

    @Test
    public void testIsNotValidDropDownSelection() {
        Editable mockEditable = mock(Editable.class);
        when(mockEditable.toString()).thenReturn("Term");

        EditText mockEditText = mock(EditText.class);
        when(mockEditText.getText()).thenReturn(mockEditable);

        TextInputLayout mockDropDownSelection = mock(TextInputLayout.class);
        when(mockDropDownSelection.getEditText()).thenReturn(mockEditText);

        boolean result = Utility.isValidDropDownSelection(mockDropDownSelection, mockItems);
        assertFalse(result);
    }

    @Test
    public void testIsValidDropDownSelectionEmpty() {
        Editable mockEditable = mock(Editable.class);
        when(mockEditable.toString()).thenReturn("");

        EditText mockEditText = mock(EditText.class);
        when(mockEditText.getText()).thenReturn(mockEditable);

        TextInputLayout mockDropDownSelection = mock(TextInputLayout.class);
        when(mockDropDownSelection.getEditText()).thenReturn(mockEditText);

        boolean result = Utility.isValidDropDownSelection(mockDropDownSelection, mockItems);
        assertFalse(result);
    }

    @Test
    public void testIsValidRadioSelection() {
        RadioGroup mockRadioGroup = mock(RadioGroup.class);
        TextView mockSelectionError = mock(TextView.class);

        when(mockRadioGroup.getCheckedRadioButtonId()).thenReturn(1);

        boolean result = Utility.isValidRadioSelection(mockRadioGroup, mockSelectionError);
        assertTrue(result);
    }

    @Test
    public void testIsValidRadioSelectionEmpty() {
        RadioGroup mockRadioGroup = mock(RadioGroup.class);
        TextView mockSelectionError = mock(TextView.class);

        when(mockRadioGroup.getCheckedRadioButtonId()).thenReturn(-1);

        boolean result = Utility.isValidRadioSelection(mockRadioGroup, mockSelectionError);
        assertFalse(result);
    }
}